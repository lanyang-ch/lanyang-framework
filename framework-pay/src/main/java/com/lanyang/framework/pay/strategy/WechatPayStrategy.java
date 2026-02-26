package com.lanyang.framework.pay.strategy;

import com.lanyang.framework.pay.config.wechat.WechatPayProperties;
import com.lanyang.framework.pay.domain.jsapi.CloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.CreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderResult;
import com.lanyang.framework.pay.domain.jsapi.QueryRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundResult;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatCloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatCreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatPreOrderResult;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatQueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatQueryOrderResult;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatQueryRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.wechat.WechatRefundResult;
import com.lanyang.framework.pay.enums.CurrencyType;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.exception.PayException;
import com.lanyang.framework.pay.util.BigDecimalConvertUtils;
import com.lanyang.framework.common.utils.BeanConvertUtils;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.CloseOrderRequest;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.jsapi.model.QueryOrderByOutTradeNoRequest;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WechatPayStrategy implements PayStrategy{

    private final WechatPayProperties wechatPayProperties;
    private final JsapiServiceExtension jsapiService;
    private final RefundService refundService;

    @Override
    public PayType getPayType() {
        return PayType.WECHAT;
    }

    @Override
    public WechatPreOrderResult createOrder(CreateOrderParam param) throws PayException{

        WechatCreateOrderParam wechatCreateOrderParam = (WechatCreateOrderParam) param;
        checkPrepayParams(wechatCreateOrderParam);
        PrepayRequest prepayRequest = buildPrepayRequest(wechatCreateOrderParam);

        try {
            // 调用接口
            PrepayWithRequestPaymentResponse response = jsapiService.prepayWithRequestPayment(prepayRequest);
            WechatPreOrderResult preOrderResult = BeanConvertUtils.sourceToTarget(response, WechatPreOrderResult.class);
            return preOrderResult;
        }catch (Exception e){
            return handleWechatPayException("查询订单", wechatCreateOrderParam.getOutTradeNo(), e);
        }

    }

    @Override
    public QueryOrderResult queryOrder(QueryOrderParam param) throws PayException {

        WechatQueryOrderParam wechatQueryOrderParam = (WechatQueryOrderParam) param;
        if(StringUtils.isBlank(wechatQueryOrderParam.getOutTradeNo())){
            throw new PayException("PARAM_ERROR", "商户订单号不能为空");
        }

        QueryOrderByOutTradeNoRequest queryRequest = new QueryOrderByOutTradeNoRequest();
        queryRequest.setOutTradeNo(wechatQueryOrderParam.getOutTradeNo());
        queryRequest.setMchid(wechatPayProperties.getMchId());
        try {
            Transaction transaction = jsapiService.queryOrderByOutTradeNo(queryRequest);
            return convert2WechatQueryOrderResult(transaction);
        }catch (Exception e){
            return handleWechatPayException("查询订单", wechatQueryOrderParam.getOutTradeNo(), e);
        }
    }

    @Override
    public void closeOrder(CloseOrderParam param) throws PayException {

        WechatCloseOrderParam wechatCloseOrderParam = (WechatCloseOrderParam) param;

        //参数校验
        if(StringUtils.isBlank(wechatCloseOrderParam.getOutTradeNo())){
            throw new PayException("PARAM_ERROR", "商户订单号不能为空");
        }

        CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
        closeOrderRequest.setOutTradeNo(wechatCloseOrderParam.getOutTradeNo());
        closeOrderRequest.setMchid(wechatPayProperties.getMchId());

        try{
            jsapiService.closeOrder(closeOrderRequest);
        }catch (Exception e){
            log.error("关闭订单失败", e);
            handleWechatPayException("关闭订单", wechatCloseOrderParam.getOutTradeNo(), e);
        }
    }

    @Override
    public void refundOrder(RefundOrderParam param) throws PayException {

        WechatRefundOrderParam wechatRefundOrderParam = (WechatRefundOrderParam) param;

        if(StringUtils.isAllBlank(wechatRefundOrderParam.getOutTradeNo(), wechatRefundOrderParam.getTradeNo())){
            throw new PayException("PARAM_ERROR", "商户订单号和微信订单号不能同时为空");
        }
        if(StringUtils.isBlank(wechatRefundOrderParam.getOutRefundNo())){
            throw new PayException("PARAM_ERROR", "商户退款单号不能为空");
        }
        if(StringUtils.isBlank(wechatRefundOrderParam.getNotifyUrl())){
            throw new PayException("PARAM_ERROR", "回调地址不能为空");
        }

        if(wechatRefundOrderParam.getTotalAmount() == null || wechatRefundOrderParam.getRefundAmount() == null){
            throw new PayException("PARAM_ERROR", "订单金额不能为空");
        }

        CreateRequest refundRequest = new CreateRequest();
        refundRequest.setTransactionId(wechatRefundOrderParam.getTradeNo());
        refundRequest.setOutTradeNo(wechatRefundOrderParam.getOutTradeNo());

        refundRequest.setOutRefundNo(wechatRefundOrderParam.getOutRefundNo());
        refundRequest.setReason(wechatRefundOrderParam.getReason());
        refundRequest.setNotifyUrl(wechatRefundOrderParam.getNotifyUrl());

        //退款金额信息
        AmountReq amount = new AmountReq();
        amount.setTotal(BigDecimalConvertUtils.convertYuan2Cent(wechatRefundOrderParam.getTotalAmount()).longValue());
        amount.setRefund(BigDecimalConvertUtils.convertYuan2Cent(wechatRefundOrderParam.getRefundAmount()).longValue());
        amount.setCurrency(CurrencyType.CNY.getCode());
        refundRequest.setAmount(amount);

        try{
            Refund refund = refundService.create(refundRequest);
            log.info("微信退款申请成功: {}", refund);
        }catch (Exception e){
            handleWechatPayException("退款申请", wechatRefundOrderParam.getOutRefundNo(), e);
        }
    }

    @Override
    public RefundResult queryRefundOrder(QueryRefundOrderParam param) throws PayException {

        WechatQueryRefundOrderParam wechatQueryRefundOrderParam = (WechatQueryRefundOrderParam) param;
        if(StringUtils.isBlank(wechatQueryRefundOrderParam.getOutRefundNo())){
            throw new PayException("PARAM_ERROR", "商户退款单号不能为空");
        }

        QueryByOutRefundNoRequest queryRequest = new QueryByOutRefundNoRequest();
        queryRequest.setOutRefundNo(wechatQueryRefundOrderParam.getOutRefundNo());
        try{
            Refund refund = refundService.queryByOutRefundNo(queryRequest);
            return BeanConvertUtils.sourceToTarget(refund, WechatRefundResult.class);
        }catch (Exception e){
            return handleWechatPayException("查询退款订单", wechatQueryRefundOrderParam.getOutRefundNo(), e);
        }
    }

    /**
     * 转换为微信查询订单结果
     * @param transaction
     * @return
     */
    @NotNull
    private WechatQueryOrderResult convert2WechatQueryOrderResult(Transaction transaction) {
        WechatQueryOrderResult queryOrderResult = new WechatQueryOrderResult();
        queryOrderResult.setAppId(transaction.getAppid());
        queryOrderResult.setMchId(transaction.getMchid());
        queryOrderResult.setOutTradeNo(transaction.getOutTradeNo());
        queryOrderResult.setTradeNo(transaction.getTransactionId());
        queryOrderResult.setTradeStatus(transaction.getTradeState().toString());
        queryOrderResult.setTradeStateDesc(transaction.getTradeStateDesc());
        queryOrderResult.setAttach(transaction.getAttach());
        queryOrderResult.setTradeType(transaction.getTradeType() == null ? null : transaction.getTradeType().toString());
        queryOrderResult.setSuccessTime(transaction.getSuccessTime());

        if(transaction.getAmount() != null){
            //用户实际支付金额
            if(transaction.getAmount().getPayerTotal() !=  null){
                queryOrderResult.setPayerTotal(BigDecimalConvertUtils.convertCent2Yuan(transaction.getAmount().getPayerTotal().toString()));
            }
            //订单总金额
            if(transaction.getAmount().getTotal() != null){
                queryOrderResult.setTotalAmount(BigDecimalConvertUtils.convertCent2Yuan(transaction.getAmount().getTotal().toString()));
            }
        }
        //订单的支付者信息，订单支付成功后返回
        queryOrderResult.setOpenId(transaction.getPayer() == null ? null : transaction.getPayer().getOpenid());

        queryOrderResult.setPromotionDetail(transaction.getPromotionDetail());
        return queryOrderResult;
    }

    /**
     * 校验微信支付下单参数
     * @param param
     */
    private void checkPrepayParams(WechatCreateOrderParam param) {

        try {
            Validate.notNull(param, "下单参数不能为空");
            Validate.notBlank(param.getOutTradeNo(), "商户订单号不能为空");
            Validate.notBlank(param.getBody(), "订单描述不能为空");
            Validate.notBlank(param.getNotifyUrl(), "回调通知地址不能为空");
            Validate.notBlank(param.getOpenId(), "支付者OpenId不能为空");
            Validate.isTrue(param.getTotalFee() != null
                    && param.getTotalFee().compareTo(BigDecimal.ZERO) > 0,
                    "订单金额必须大于0");
            Validate.notBlank(wechatPayProperties.getAppId(), "微信支付AppId未配置");
            Validate.notBlank(wechatPayProperties.getMchId(), "微信支付商户号未配置");
        } catch (IllegalArgumentException e) {
            throw new PayException(e.getMessage());
        }
    }

    /**
     * 组装微信支付PrepayRequest参数
     * @param param
     * @return
     */
    private PrepayRequest buildPrepayRequest(WechatCreateOrderParam param) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(wechatPayProperties.getAppId());
        prepayRequest.setMchid(wechatPayProperties.getMchId());
        prepayRequest.setOutTradeNo(param.getOutTradeNo());
        prepayRequest.setDescription(param.getBody());
        prepayRequest.setNotifyUrl(param.getNotifyUrl());
        //金额
        Amount amount = new Amount();
        amount.setTotal(BigDecimalConvertUtils.convertYuan2Cent(param.getTotalFee()));
        amount.setCurrency(CurrencyType.CNY.getCode());
        prepayRequest.setAmount(amount);

        //支付者信息
        Payer payer = new Payer();
        payer.setOpenid(param.getOpenId());
        prepayRequest.setPayer(payer);
        return prepayRequest;
    }

    /**
     * 处理微信支付异常
     * @param action 操作类型
     * @param outTradeNo 商户订单号
     * @param e 异常
     * @throws PayException
     */
    private <T> T handleWechatPayException(String action, String outTradeNo, Exception e) throws PayException {
        String logPrefix = String.format("[微信%s] 商户订单号：%s - ", action, outTradeNo);
        if (e instanceof HttpException) {
            log.error(logPrefix + "HTTP请求失败", e);
            throw new PayException("HTTP_REQUEST_FAIL", String.format("%s失败：网络请求异常", action));
        } else if (e instanceof ServiceException) {
            // 服务返回状态小于200或大于等于300，例如500
            ServiceException se = (ServiceException) e;
            log.error(logPrefix + "业务服务失败", e);
            // 微信支付SDK的错误码/消息可直接透传（若需脱敏可在此处理）
            //e.getMessage(),里会有敏感信息，如需返回，请自行处理
            throw new PayException(se.getErrorCode(), String.format("%s失败：%s", action, se.getErrorMessage()));
        } else if (e instanceof MalformedMessageException) {
            // 服务返回成功，返回体类型不合法，或者解析返回体失败
            log.error(logPrefix + "响应结果解析失败", e);
            throw new PayException("RESPONSE_PARSE_FAIL", String.format("%s失败：返回结果格式异常", action));
        } else {
            log.error(logPrefix + "未知错误", e);
            throw new PayException("UNKNOWN_ERROR", String.format("%s失败：系统异常", action));
        }
    }

}
