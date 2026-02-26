package com.lanyang.framework.pay.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Slf4j
public class AliCreditUtils extends AliPayUtils{

//    private static final ApiClient DEFAULT_CLIENT = null;
//
//    /**
//     * 创建免押订单
//     * @param creditOrderModel
//     * @return
//     */
//    public static AliCreditDepositResult createDepositOrder(AliCreditOrderModel creditOrderModel){
//
//        GenericExecuteApi api = new GenericExecuteApi(DEFAULT_CLIENT);
//
//        // 构造请求参数以调用接口
//        Map<String, Object> bizParams = new HashMap<>();
//        Map<String, Object> bizContent = new HashMap<>();
//
//        // 设置订单标题
//        bizContent.put("order_title", creditOrderModel.getOrderTitle());
//
//        // 设置商户授权资金订单号
//        bizContent.put("out_order_no", creditOrderModel.getOutOrderNo());
//
//        // 设置商户本次资金操作的请求流水号，用于标示请求流水的唯一性。可与out_order_no相同
//        bizContent.put("out_request_no", StringUtils.isBlank(creditOrderModel.getOutRequestNo())? creditOrderModel.getOutOrderNo() : creditOrderModel.getOutRequestNo());
//
//        // 设置需要冻结的金额
//        bizContent.put("amount", creditOrderModel.getTotalAmount());
//
//        // 设置预授权订单相对超时时间
//        bizContent.put("timeout_express", creditOrderModel.getTimeoutExpress());
//
//        // 设置销售产品码
//        bizContent.put("product_code", ProductCode.PRE_AUTH_ONLINE.getCode());
//
//        // 设置业务扩展参数
//        bizContent.put("extra_param", "{\"category\":\"" + creditOrderModel.getCategory() + "\",\"serviceId\":\"" + creditOrderModel.getServiceId() +"\"}");
//
//        // 设置免押受理台模式
//        bizContent.put("deposit_product_mode", DepositProductMode.DEPOSIT_ONLY.getCode());
//
//        bizParams.put("biz_content", bizContent);
//
//        //回调通知地址
//        bizParams.put("notify_url", creditOrderModel.getNotifyUrl());
//
//        try {
//            // 如果是第三方代调用模式，请设置app_auth_token（应用授权令牌）
//            String orderStr = api.sdkExecute("alipay.fund.auth.order.app.freeze", bizParams, creditOrderModel.getAccessToken(), null, null);
//            log.info("线上资金授权冻结接口:{}", orderStr);
//            AliCreditDepositResult depositResult = new AliCreditDepositResult();
//            depositResult.setResultCode(CommonTradeStatus.SUCCESS);
//            depositResult.setOrderNo(creditOrderModel.getOutOrderNo());
//            depositResult.setOrderStr(orderStr);
//            return depositResult;
//        } catch (ApiException e) {
//            AliCreditDepositResult depositResult = new AliCreditDepositResult();
//            depositResult.setResultCode(CommonTradeStatus.FAIL);
//            depositResult.setErrorCode(String.valueOf(e.getCode()));
//            depositResult.setErrorMsg(e.getMessage());
//            return depositResult;
//        }
//    }
//
//    /**
//     * 通过该接口可以查询单笔明细的详细信息
//     * 1、请求与响应参数中的 operation_type 均表示当前查询的资金操作类型
//     * 2、查询冻结明细时，out_request_no 传入发起冻结操作时的 out_request_no，operation_type 默认为FREEZE，可不传入；
//     * 3、查询解冻明细时，out_request_no 传入发起解冻操作时的 out_request_no，operation_type 需传入UNFREEZE；
//     * 4、查询支付明细时，out_request_no 需要传入发起转支付时传入的 out_trade_no 参数值，operation_type 需传入PAY；
//     * 5、如果是使用 complete 模式发起的转支付（转交易的同时剩余金额解冻），需要查询关联的解冻明细，此时 out_request_no 同样传入转支付时传入的 out_trade_no，operation_type 需传入 UNFREEZE；
//     * 6、注意区分查询结果中返回的 status 和 order_status 字段，status 表示当前资金操作的状态；order_status 表示当前授权单的状态，详细状态枚举见参数描述。
//     * @param queryModel
//     * @return
//     */
//    public static AlipayFundAuthOperationDetailQueryResponseModel queryCreditOrder(AliCreditOrderQueryModel queryModel) {
//
//        // 构造请求参数以调用接口
//        AlipayFundAuthOperationDetailApi api = new AlipayFundAuthOperationDetailApi(DEFAULT_CLIENT);
//        AlipayFundAuthOperationDetailQueryModel data = new AlipayFundAuthOperationDetailQueryModel();
//
//        // 设置支付宝授权资金订单号
//        data.setAuthNo(queryModel.getAuthNo());
//
//        // 设置商户的授权资金订单号
//        data.setOutOrderNo(queryModel.getOutOrderNo());
//
//        // 设置支付宝的授权资金操作流水号
//        data.setOperationId(queryModel.getOperationId());
//
//        // 设置商户的授权资金操作流水号
//        data.setOutRequestNo(queryModel.getOutRequestNo());
//
//        // 设置需要查询的授权资金操作类型
//        data.setOperationType(queryModel.getOperationType().getCode());
//
//        try {
//            AlipayFundAuthOperationDetailQueryResponseModel response = api.query(data, null);
//            return response;
//        } catch (ApiException e) {
//            AlipayFundAuthOperationDetailQueryDefaultResponse errorObject = (AlipayFundAuthOperationDetailQueryDefaultResponse) e.getErrorObject();
//            if(AliCreditStatus.AUTH_ORDER_NOT_EXIST.getCode().equals(errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getCode().getValue())){
//                AlipayFundAuthOperationDetailQueryResponseModel response = new AlipayFundAuthOperationDetailQueryResponseModel();
//                response.setStatus(AliPayStatus.SUCCESS.getCode());
//                response.setOrderStatus(AliCreditStatus.AUTH_ORDER_NOT_EXIST.getCode());
//                return response;
//            }
//            log.error("调用查询授权失败: {}", errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getCode());
//            throw new PayException(errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getMessage());
//        }
//    }
//
//    public static AliCreditQueryResult retryQueryCreditOrder(AliCreditOrderQueryModel queryModel, int maxRetries) {
//        // 构造请求参数以调用接口
//        AlipayFundAuthOperationDetailApi api = new AlipayFundAuthOperationDetailApi(DEFAULT_CLIENT);
//        AlipayFundAuthOperationDetailQueryModel data = new AlipayFundAuthOperationDetailQueryModel();
//
//        // 设置支付宝授权资金订单号
//        data.setAuthNo(queryModel.getAuthNo());
//
//        // 设置商户的授权资金订单号
//        data.setOutOrderNo(queryModel.getOutOrderNo());
//
//        // 设置支付宝的授权资金操作流水号
//        data.setOperationId(queryModel.getOperationId());
//
//        // 设置商户的授权资金操作流水号
//        data.setOutRequestNo(queryModel.getOutRequestNo());
//
//        // 设置需要查询的授权资金操作类型
//        data.setOperationType(queryModel.getOperationType().getCode());
//
//        for (int i = 0; i <= maxRetries; i++) {
//            try {
//                AlipayFundAuthOperationDetailQueryResponseModel response = api.query(data, null);
//
//                AliCreditQueryResult queryResult = new AliCreditQueryResult();
//                queryResult.setOrderNo(queryResult.getOrderNo());
//                queryResult.setStatus(CommonTradeStatus.SUCCESS.getCode());
//                queryResult.setAuthNo(response.getAuthNo());
//                queryResult.setOrderStatus(response.getOrderStatus());
//                queryResult.setOperationId(response.getOperationId());
//                return queryResult;
//            } catch (ApiException e) {
//                log.error("支付宝查询第{}次查询失败，订单号：{}", i + 1, queryModel.getOutOrderNo(), e);
//                if (i < maxRetries) {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(1000L);
//                    } catch (InterruptedException ie) {
//                        Thread.currentThread().interrupt();
//                        return null;
//                    }
//                }else{
//                    // 最后一次失败不重试
//                    AlipayFundAuthOperationDetailQueryDefaultResponse errorObject = (AlipayFundAuthOperationDetailQueryDefaultResponse) e.getErrorObject();
//                    AliCreditQueryResult queryResult = new AliCreditQueryResult();
//                    queryResult.setOrderNo(queryResult.getOrderNo());
//                    queryResult.setStatus(CommonTradeStatus.FAIL.getCode());
//                    queryResult.setErrorCode(errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getCode().getValue());
//                    queryResult.setErrorMsg(errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getMessage());
//                    return queryResult;
//                }
//            }
//        }
//        AliCreditQueryResult queryResult = new AliCreditQueryResult();
//        queryResult.setOrderNo(queryResult.getOrderNo());
//        queryResult.setStatus(CommonTradeStatus.FAIL.getCode());
//        return queryResult;
//    }
//
//    /**
//     * 解析支付宝免押回调
//     * @param params
//     * @return
//     */
//    public static AlipayDepositNotify parseDepositNotify(Map<String , String> params){
//
//        //公钥验签示例代码
//        try {
//            boolean  signVerified = AlipaySignature.verifyV1(params, AliPayInitProperties.getPublicKey(), "UTF-8","RSA2") ;
//            if (!signVerified){
//                throw new PayException("验证签名失败");
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//            throw new PayException("验证签名失败");
//
//        }
//
//        AlipayDepositNotify aliPayNotify = JsonUtils.formatJson2Bean(JsonUtils.toJsonString(params), AlipayDepositNotify.class);
//        return aliPayNotify;
//    }
//
//    /**
//     * 取消免押订单
//     * 只有商户由于业务系统处理超时需要终止后续业务处理或者授权结果未知时可调用撤销，
//     * 其他正常授权冻结的操作如需实现相同功能请调用资金授权解冻服务。
//     * 提交资金授权后调用【资金授权操作查询】，没有明确的授权结果再调用【资金授权撤销】
//     * 1、建议先调用查询接口确认冻结是否成功，如果确认冻结成功后需要解冻，请使用解冻接口；
//     * 2、当且仅当无法确认冻结操作是否成功时需要调用撤销接口；
//     * 2、发生冻结操作后24小时内可调用撤销，超过24小时不可再做撤销；
//     * @param cancelModel
//     * @return
//     */
//    public static AlipayFundAuthOperationCancelResponseModel cancelDepositOrder(AliCreditOrderCancelModel cancelModel){
//
//        // 构造请求参数以调用接口
//        AlipayFundAuthOperationApi api = new AlipayFundAuthOperationApi(DEFAULT_CLIENT);
//        AlipayFundAuthOperationCancelModel data = new AlipayFundAuthOperationCancelModel();
//
//        // 设置支付宝授权资金订单号
//        data.setAuthNo(cancelModel.getAuthNo());
//
//        // 设置商户的授权资金订单号
//        data.setOutOrderNo(cancelModel.getOutOrderNo());
//
//        // 设置支付宝的授权资金操作流水号
//        data.setOperationId(cancelModel.getOperationId());
//
//        // 设置商户的授权资金操作流水号
//        data.setOutRequestNo(cancelModel.getOutRequestNo());
//
//        // 设置商户对本次撤销操作的附言描述
//        data.setRemark(cancelModel.getRemark());
//
//        data.setNotifyUrl(cancelModel.getNotifyUrl());
//
//
//        try {
//            AlipayFundAuthOperationCancelResponseModel response = api.cancel(data, null);
//            return response;
//        } catch (ApiException e) {
//            AlipayFundAuthOperationDetailQueryDefaultResponse errorObject = (AlipayFundAuthOperationDetailQueryDefaultResponse) e.getErrorObject();
//            throw new PayException(errorObject.getAlipayFundAuthOperationDetailQueryErrorResponseModel().getMessage());
//        }
//    }
//
//
//    /**
//     * 解析支付宝免押取消回调
//     * @param params
//     * @return
//     */
//    public static AlipayFundAuthOperationCancelResponseModel parseCancelDepositNotify(Map<String , String> params){
//
//        //公钥验签示例代码
//        try {
//            boolean  signVerified = AlipaySignature.verifyV1(params, AliPayInitProperties.getPublicKey(), "UTF-8","RSA2") ;
//            if (!signVerified){
//                throw new PayException("验证签名失败");
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//            throw new PayException("验证签名失败");
//
//        }
//
//        AlipayFundAuthOperationCancelResponseModel aliPayNotify = JsonUtils.formatJson2Bean(JsonUtils.toJsonString(params), AlipayFundAuthOperationCancelResponseModel.class);
//        return aliPayNotify;
//    }
//
//    /**
//     * 统一收单交易支付接口
//     * 用户在商户侧已授权下单并享受服务后，商户使用授权单号通过本接口对用户已授权金额发起扣款
//     * @param tradeDeductModel
//     * @return
//     */
//    public static TradePayResult tradePay(AliTradeDeductModel tradeDeductModel){
//
//        // 构造请求参数以调用接口
//        AlipayTradeApi api = new AlipayTradeApi(DEFAULT_CLIENT);
//        AlipayTradePayModel data = new AlipayTradePayModel();
//
//        // 设置商户订单号
//        data.setOutTradeNo(tradeDeductModel.getOutTradeNo());
//
//        // 设置订单总金额
//        data.setTotalAmount(tradeDeductModel.getTotalAmount());
//
//        // 设置订单标题
//        data.setSubject(tradeDeductModel.getSubject());
//
//        // 设置资金预授权单号
//        data.setAuthNo(tradeDeductModel.getAuthNo());
//
//        // 设置产品码
//        data.setProductCode(ProductCode.PREAUTH_PAY.getCode());
//
//        // 设置预授权确认模式
//        data.setAuthConfirmMode(tradeDeductModel.getAuthConfirmMode().getCode());
//
//        // 设置买家支付宝用户唯一标识
//        data.setBuyerOpenId(tradeDeductModel.getOpenId());
//        data.setNotifyUrl(tradeDeductModel.getNotifyUrl());
//
////        data.setDisablePayChannels("moneyFund,coupon,pcredit,pcreditpayInstallment,creditCard,creditCardExpress,creditCardCartoon,credit_group,debitCardExpress,mcard,pcard,promotion,voucher,point,mdiscount,bankPay");
//
//        try {
//            AlipayTradePayResponseModel response = api.pay(data, null);
//            TradePayResult tradePayResult = new TradePayResult();
//            tradePayResult.setTradeNo(response.getTradeNo());
//            tradePayResult.setTradeStatus(CommonTradeStatus.SUCCESS.getCode());
//            tradePayResult.setOrderNo(tradeDeductModel.getOutTradeNo());
//            tradePayResult.setTotalAmount(response.getTotalAmount());
//            return tradePayResult;
//        } catch (ApiException e) {
//            AlipayTradePayDefaultResponse errorObject = (AlipayTradePayDefaultResponse) e.getErrorObject();
//            TradePayResult tradePayResult = new TradePayResult();
//            tradePayResult.setTradeStatus(CommonTradeStatus.FAIL.getCode());
//            tradePayResult.setOrderNo(tradeDeductModel.getOutTradeNo());
//            tradePayResult.setTotalAmount(tradeDeductModel.getTotalAmount());
//            tradePayResult.setErrorCode(errorObject.getAlipayTradePayErrorResponseModel().getCode().getValue());
//            tradePayResult.setErrorMsg(errorObject.getAlipayTradePayErrorResponseModel().getMessage());
//            return tradePayResult;
//        }
//    }
//
//    /**
//     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭
//     * @param orderNo 商户订单号
//     */
//    public static TradeCloseResult tradeClose(String orderNo){
//
//        // 构造请求参数以调用接口
//        AlipayTradeApi api = new AlipayTradeApi(DEFAULT_CLIENT);
//        AlipayTradeCloseModel data = new AlipayTradeCloseModel();
//
//        // 设置订单支付时传入的商户订单号
//        data.setOutTradeNo(orderNo);
//
//        try {
//            AlipayTradeCloseResponseModel response = api.close(data, null);
//            TradeCloseResult closeResult = new TradeCloseResult();
//            closeResult.setOrderNo(orderNo);
//            closeResult.setTradeNo(response.getTradeNo());
//            closeResult.setTradeStatus(CommonTradeStatus.SUCCESS.getCode());
//            return closeResult;
//        } catch (ApiException e) {
//            AlipayTradeCloseDefaultResponse errorObject = (AlipayTradeCloseDefaultResponse) e.getErrorObject();
//            TradeCloseResult closeResult = new TradeCloseResult();
//            closeResult.setOrderNo(orderNo);
//            closeResult.setTradeStatus(CommonTradeStatus.FAIL.getCode());
//            closeResult.setErrorMsg(errorObject.getAlipayTradeCloseErrorResponseModel().getMessage());
//            closeResult.setErrorCode(errorObject.getAlipayTradeCloseErrorResponseModel().getCode().getValue());
//            return closeResult;
//        }
//    }
//
//
//    /**
//     * 查询免押订单扣款记录
//     * @param orderNo
//     * @return
//     */
//    public static TradePayResult queryTradePay(String orderNo){
//
//        // 构造请求参数以调用接口
//        AlipayTradeApi api = new AlipayTradeApi(DEFAULT_CLIENT);
//        AlipayTradeQueryModel data = new AlipayTradeQueryModel();
//
//        // 设置订单支付时传入的商户订单号
//        data.setOutTradeNo(orderNo);
//
//        try {
//            AlipayTradeQueryResponseModel response = api.query(data, null);
//            TradePayResult tradePayResult = new TradePayResult();
//            tradePayResult.setTradeStatus(response.getTradeStatus());
//            tradePayResult.setOrderNo(response.getOutTradeNo());
//            tradePayResult.setTradeNo(response.getTradeNo());
//            tradePayResult.setTotalAmount(response.getTotalAmount());
//            return tradePayResult;
//        } catch (ApiException e) {
//            AlipayTradeQueryDefaultResponse errorObject = (AlipayTradeQueryDefaultResponse) e.getErrorObject();
//            log.error("查询支付宝交易状态失败: {},{},{}",
//                    orderNo,
//                    errorObject.getAlipayTradeQueryErrorResponseModel().getCode().getValue(),
//                    errorObject.getAlipayTradeQueryErrorResponseModel().getMessage());
//            return null;
//        }
//    }
//
//    public static TradePayResult retryQueryTradePay(String orderNo, int maxRetries){
//        // 构造请求参数以调用接口
//        AlipayTradeApi api = new AlipayTradeApi(DEFAULT_CLIENT);
//        AlipayTradeQueryModel data = new AlipayTradeQueryModel();
//
//        // 设置订单支付时传入的商户订单号
//        data.setOutTradeNo(orderNo);
//        for (int i = 0; i <= maxRetries; i++) {
//            try {
//                AlipayTradeQueryResponseModel response = api.query(data, null);
//                TradePayResult tradePayResult = new TradePayResult();
//                tradePayResult.setTradeStatus(response.getTradeStatus());
//                tradePayResult.setOrderNo(response.getOutTradeNo());
//                tradePayResult.setTradeNo(response.getTradeNo());
//                tradePayResult.setTotalAmount(response.getTotalAmount());
//                return tradePayResult;
//            } catch (ApiException e) {
//                log.error("支付宝查询第{}次查询失败，订单号：{}", i + 1, orderNo, e);
//                if (i < maxRetries) { // 最后一次失败不重试
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(1000L);
//                    } catch (InterruptedException ie) {
//                        Thread.currentThread().interrupt();
//                        return null;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 资金授权解冻
//     * @param unfreezeModel
//     * @return
//     */
//    public static AliDepositUnfreezeResult unfreeze(AliCreditOrderUnfreezeModel unfreezeModel){
//
//        // 构造请求参数以调用接口
//        AlipayFundAuthOrderApi api = new AlipayFundAuthOrderApi(DEFAULT_CLIENT);
//        AlipayFundAuthOrderUnfreezeModel data = new AlipayFundAuthOrderUnfreezeModel();
//
//        // 设置支付宝资金授权订单号
//        data.setAuthNo(unfreezeModel.getAuthNo());
//
//        // 设置解冻请求流水号
//        data.setOutRequestNo(unfreezeModel.getOutRequestNo());
//
//        // 设置本次操作解冻的金额
//        data.setAmount(unfreezeModel.getAmount());
//
//        // 设置商户对本次解冻操作的附言描述
//        data.setRemark(unfreezeModel.getRemark());
//
//        data.setNotifyUrl(unfreezeModel.getNotifyUrl());
//
//        try {
//            AlipayFundAuthOrderUnfreezeResponseModel response = api.unfreeze(data, null);
//            AliDepositUnfreezeResult unfreezeResult = new AliDepositUnfreezeResult();
//            unfreezeResult.setAmount(response.getAmount());
//            unfreezeResult.setCreditAmount(response.getCreditAmount());
//            unfreezeResult.setAuthNo(response.getAuthNo());
//            unfreezeResult.setFundAmount(response.getFundAmount());
//            unfreezeResult.setGmtTrans(response.getGmtTrans());
//            unfreezeResult.setStatus(response.getStatus());
//            unfreezeResult.setOperationId(response.getOperationId());
//            unfreezeResult.setOutRequestNo(response.getOutRequestNo());
//            unfreezeResult.setOutOrderNo(response.getOutOrderNo());
//            return unfreezeResult;
//        } catch (ApiException e) {
//            AlipayFundAuthOrderUnfreezeDefaultResponse errorObject = (AlipayFundAuthOrderUnfreezeDefaultResponse) e.getErrorObject();
//            AliDepositUnfreezeResult unfreezeResult = new AliDepositUnfreezeResult();
//            unfreezeResult.setStatus(CommonTradeStatus.FAIL.getCode());
//            unfreezeResult.setOutOrderNo(unfreezeModel.getOutRequestNo());
//            unfreezeResult.setErrorCode(errorObject.getAlipayFundAuthOrderUnfreezeErrorResponseModel().getCode().getValue());
//            unfreezeResult.setErrorMsg(errorObject.getAlipayFundAuthOrderUnfreezeErrorResponseModel().getMessage());
//            return unfreezeResult;
//        }
//    }
//
//    public static AliCreditUnfreezeNotify parseCreditUnfreezeNotify(Map<String , String> params){
//        //公钥验签示例代码
//        try {
//            boolean  signVerified = AlipaySignature.verifyV1(params, AliPayInitProperties.getPublicKey(), "UTF-8","RSA2") ;
//            if (!signVerified){
//                throw new PayException("验证签名失败");
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//            throw new PayException("验证签名失败");
//
//        }
//
//        AliCreditUnfreezeNotify aliPayNotify = JsonUtils.formatJson2Bean(JsonUtils.toJsonString(params), AliCreditUnfreezeNotify.class);
//        return aliPayNotify;
//    }
//
//    public static void main(String[] args) {
//        AliCreditOrderModel creditOrderModel = new AliCreditOrderModel();
//        creditOrderModel.setOrderTitle("测试");
//        creditOrderModel.setOutOrderNo("202303");
//        creditOrderModel.setTotalAmount("12.34");
////        creditOrderModel.setCategory("CHARGE_PILE_CA");
//        creditOrderModel.setServiceId("2025092900000000000116297700");
//        creditOrderModel.setNotifyUrl("https://www.baidu.com");
//        AliCreditDepositResult depositResult = createDepositOrder(creditOrderModel);
//        System.out.println(depositResult);
//
//    }
}
