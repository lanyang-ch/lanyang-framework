package com.lanyang.framework.pay.strategy;

import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.api.AlipayTradeFastpayRefundApi;
import com.alipay.v3.model.AlipayTradeCloseDefaultResponse;
import com.alipay.v3.model.AlipayTradeCloseModel;
import com.alipay.v3.model.AlipayTradeCreateDefaultResponse;
import com.alipay.v3.model.AlipayTradeCreateModel;
import com.alipay.v3.model.AlipayTradeCreateResponseModel;
import com.alipay.v3.model.AlipayTradeFastpayRefundQueryDefaultResponse;
import com.alipay.v3.model.AlipayTradeFastpayRefundQueryModel;
import com.alipay.v3.model.AlipayTradeFastpayRefundQueryResponseModel;
import com.alipay.v3.model.AlipayTradeQueryDefaultResponse;
import com.alipay.v3.model.AlipayTradeQueryModel;
import com.alipay.v3.model.AlipayTradeQueryResponseModel;
import com.alipay.v3.model.AlipayTradeRefundDefaultResponse;
import com.alipay.v3.model.AlipayTradeRefundModel;
import com.alipay.v3.model.AlipayTradeRefundResponseModel;
import com.lanyang.framework.pay.config.ali.AliPayInitProperties;
import com.lanyang.framework.pay.domain.jsapi.CloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.CreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderResult;
import com.lanyang.framework.pay.domain.jsapi.QueryRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundResult;
import com.lanyang.framework.pay.domain.jsapi.ali.AliCloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.ali.AliCreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.ali.AliPreOrderResult;
import com.lanyang.framework.pay.domain.jsapi.ali.AliQueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.ali.AliQueryOrderResult;
import com.lanyang.framework.pay.domain.jsapi.ali.AliQueryRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.ali.AliRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.ali.AliRefundOrderResult;
import com.lanyang.framework.pay.enums.CommonTradeStatus;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.enums.ali.ProductCode;
import com.lanyang.framework.pay.exception.PayException;
import com.lanyang.framework.common.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des 支付宝支付
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AliPayStrategy implements PayStrategy {

    private final AliPayInitProperties aliPayInitProperties;
    private final AlipayTradeApi alipayTradeApi;
    private final AlipayTradeFastpayRefundApi alipayTradeFastpayRefundApi;

    @Override
    public PayType getPayType() {
        return PayType.ALIPAY;
    }

    @Override
    public AliPreOrderResult createOrder(CreateOrderParam param) throws PayException{

        AliCreateOrderParam aliCreateOrderParam = (AliCreateOrderParam) param;

        //TODO 参数校验

        AlipayTradeCreateModel data = new AlipayTradeCreateModel();
        // 设置商户订单号
        data.setOutTradeNo(aliCreateOrderParam.getOutTradeNo());
        // 设置订单总金额
        data.setTotalAmount(aliCreateOrderParam.getTotalFee().toString());
        // 设置订单标题
        data.setSubject(aliCreateOrderParam.getBody());
        // 设置订单相对超时时间
        data.setTimeoutExpress(aliCreateOrderParam.getTimeoutExpress());
        // 设置买家支付宝用户唯一标识
        data.setBuyerOpenId(aliCreateOrderParam.getOpenId());
        // 设置产品码
        data.setProductCode(ProductCode.JSAPI_PAY.getCode());
        //设置异步回调地址
        data.setNotifyUrl(aliCreateOrderParam.getNotifyUrl());
        data.setOpAppId(aliPayInitProperties.getAppId());
        try {
            AlipayTradeCreateResponseModel response = alipayTradeApi.create(data);
            AliPreOrderResult aliPreOrderResult = new AliPreOrderResult();
            aliPreOrderResult.setResultCode(CommonTradeStatus.SUCCESS);
            aliPreOrderResult.setTradeNo(response.getTradeNo());
            aliPreOrderResult.setOutTradeNo(aliCreateOrderParam.getOutTradeNo());
            return aliPreOrderResult;
        } catch (ApiException e) {
            log.error("支付宝创建订单失败", e);
            AlipayTradeCreateDefaultResponse errorObject = (AlipayTradeCreateDefaultResponse) e.getErrorObject();
            throw new PayException(errorObject.getAlipayTradeCreateErrorResponseModel().getCode().getValue(),
                    errorObject.getAlipayTradeCreateErrorResponseModel().getMessage());
        }catch (Exception e){
            log.error("支付宝创建订单失败", e);
            throw new PayException("UNKNOWN_ERROR", e.getMessage());
        }
    }

    @Override
    public QueryOrderResult queryOrder(QueryOrderParam param) throws PayException {

        AliQueryOrderParam aliQueryOrderParam = (AliQueryOrderParam) param;
        if(StringUtils.isAllBlank(aliQueryOrderParam.getOutTradeNo(), aliQueryOrderParam.getTradeNo())){
            log.error("查询订单参数错误 {}", aliQueryOrderParam);
            throw new PayException("PARAM_ERROR", "查询订单参数错误");
        }

        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo(aliQueryOrderParam.getOutTradeNo());
        queryModel.setTradeNo(aliQueryOrderParam.getTradeNo());
        if(CollectionUtils.isNotEmpty(aliQueryOrderParam.getQueryOptions())){
            queryModel.setQueryOptions(aliQueryOrderParam.getQueryOptions().stream().map(item -> item.getCode()).collect(Collectors.toList()));
        }
        try {
            AlipayTradeQueryResponseModel responseModel = alipayTradeApi.query(queryModel);
            AliQueryOrderResult aliQueryOrderResult = new AliQueryOrderResult();
            aliQueryOrderResult.setResultCode(CommonTradeStatus.SUCCESS);
            aliQueryOrderResult.setTradeStatus(responseModel.getTradeStatus());
            aliQueryOrderResult.setOutTradeNo(aliQueryOrderParam.getOutTradeNo());
            aliQueryOrderResult.setTradeNo(responseModel.getTradeNo());
            aliQueryOrderResult.setTotalAmount(new BigDecimal(responseModel.getTotalAmount()));
            aliQueryOrderResult.setBuyerUserId(responseModel.getBuyerUserId());
            aliQueryOrderResult.setOpenId(responseModel.getBuyerOpenId());
            aliQueryOrderResult.setFundBillList(responseModel.getFundBillList());
            return aliQueryOrderResult;
        } catch (ApiException e) {
            log.error("支付宝查询订单失败", e);
            AlipayTradeQueryDefaultResponse errorObject = (AlipayTradeQueryDefaultResponse) e.getErrorObject();
            throw new PayException(errorObject.getAlipayTradeQueryErrorResponseModel().getCode().getValue(),
                    errorObject.getAlipayTradeQueryErrorResponseModel().getMessage());
        }catch (Exception e){
            log.error("支付宝查询订单失败", e);
            throw new PayException("UNKNOWN_ERROR", e.getMessage());
        }
    }

    @Override
    public void closeOrder(CloseOrderParam param) throws PayException {

        AliCloseOrderParam aliCloseOrderParam = (AliCloseOrderParam) param;

        if(StringUtils.isAllBlank(aliCloseOrderParam.getOutTradeNo(), aliCloseOrderParam.getTradeNo())){
            throw new PayException("PARAM_ERROR", "关闭订单参数错误");
        }

        AlipayTradeCloseModel alipayTradeCloseModel = new AlipayTradeCloseModel();
        alipayTradeCloseModel.setTradeNo(aliCloseOrderParam.getTradeNo());
        alipayTradeCloseModel.setOutTradeNo(aliCloseOrderParam.getOutTradeNo());
        try {
            alipayTradeApi.close(alipayTradeCloseModel);
        } catch (ApiException e) {
            log.error("支付宝关闭订单失败", e);
            AlipayTradeCloseDefaultResponse errorObject = (AlipayTradeCloseDefaultResponse) e.getErrorObject();
            throw new PayException(errorObject.getAlipayTradeCloseErrorResponseModel().getCode().getValue(),
                    errorObject.getAlipayTradeCloseErrorResponseModel().getMessage());
        }catch (Exception e){
            log.error("支付宝关闭订单失败", e);
            throw new PayException("UNKNOWN_ERROR", e.getMessage());
        }
    }

    @Override
    public void refundOrder(RefundOrderParam param) throws PayException {

        AliRefundOrderParam aliRefundOrderParam = (AliRefundOrderParam) param;

        //TODO 参数校验

        // 构造请求参数以调用接口
        AlipayTradeRefundModel data = new AlipayTradeRefundModel();
        // 设置原支付请求的商户订单号与支付宝交易号二选一
        data.setOutTradeNo(aliRefundOrderParam.getOutTradeNo());
        // 设置支付宝交易号
        data.setTradeNo(aliRefundOrderParam.getTradeNo());

        //退款金额
        data.setRefundAmount(aliRefundOrderParam.getRefundAmount().toString());

        //退款原因
        data.setRefundReason(aliRefundOrderParam.getReason());
        //退款请求号。 标识一次退款请求，需要保证在交易号下唯一，如需部分退款，则此参数必传。 注：针对同一次退款请求，
        // 如果调用接口失败或异常了，重试时需要保证退款请求号不能变更，防止该笔交易重复退款。支付宝会保证同样的退款请求号多次请求只会退一次
        data.setOutRequestNo(aliRefundOrderParam.getOutRefundNo());
//        boolean signVerified = AlipaySignature.verify(); //调用SDK验证签名
        try {
            AlipayTradeRefundResponseModel response = alipayTradeApi.refund(data);
            log.info("支付宝退款订单成功: {}", response);
        } catch (ApiException e) {

            // 获取支付宝返回的错误对象
            AlipayTradeRefundDefaultResponse errorObject = (AlipayTradeRefundDefaultResponse) e.getErrorObject();
            throw new PayException(errorObject.getAlipayTradeRefundErrorResponseModel().getCode().getValue(), errorObject.getAlipayTradeRefundErrorResponseModel().getMessage());
        }catch (Exception e){
            log.error("支付宝退款订单失败", e);
            throw new PayException("UNKNOWN_ERROR", e.getMessage());
        }
    }

    @Override
    public RefundResult queryRefundOrder(QueryRefundOrderParam param) throws PayException {

        AliQueryRefundOrderParam aliQueryRefundOrderParam = (AliQueryRefundOrderParam) param;

        if(StringUtils.isAllBlank(aliQueryRefundOrderParam.getOutTradeNo(), aliQueryRefundOrderParam.getTradeNo())){
            throw new PayException("PARAM_ERROR", "查询订单参数错误");
        }
        if(StringUtils.isBlank(aliQueryRefundOrderParam.getOutRefundNo())){
            throw new PayException("PARAM_ERROR", "商户退款单号不能为空");
        }

        AlipayTradeFastpayRefundQueryModel refundQueryModel = new AlipayTradeFastpayRefundQueryModel();
        // 商户订单号 和 支付宝订单号 二选一
        refundQueryModel.setOutTradeNo(aliQueryRefundOrderParam.getOutTradeNo());
        refundQueryModel.setTradeNo(aliQueryRefundOrderParam.getTradeNo());
        //退款请求号。 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的商户订单号
        refundQueryModel.setOutRequestNo(aliQueryRefundOrderParam.getOutRefundNo());
        try {
            AlipayTradeFastpayRefundQueryResponseModel responseModel = alipayTradeFastpayRefundApi.query(refundQueryModel);
            return BeanConvertUtils.sourceToTarget(responseModel, AliRefundOrderResult.class);
        } catch (ApiException e) {
            AlipayTradeFastpayRefundQueryDefaultResponse errorObject = (AlipayTradeFastpayRefundQueryDefaultResponse) e.getErrorObject();
            throw new PayException(errorObject.getAlipayTradeFastpayRefundQueryErrorResponseModel().getCode().getValue(),
                    errorObject.getAlipayTradeFastpayRefundQueryErrorResponseModel().getMessage());
        }catch (Exception e){
            log.error("支付宝查询退款订单失败", e);
            throw new PayException("UNKNOWN_ERROR", e.getMessage());
        }

    }
}
