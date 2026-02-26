package com.lanyang.framework.pay.domain.credit.ali;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
public class AlipayDepositNotify {

    @JsonProperty("notify_type")
    public String notifyType;

    @JsonProperty("notify_id")
    public String notifyId;

    @JsonProperty("notify_time")
    public String notifyTime;

    @JsonProperty("sign_type")
    public String signType;

    public String sign;

    @JsonProperty("auth_no")
    public String authNo;

    @JsonProperty("out_order_no")
    public String outOrderNo;

    @JsonProperty("operation_id")
    public String operationId;

    @JsonProperty("out_request_no")
    public String outRequestNo;

    @JsonProperty("operation_type")
    public String operationType;

    public String amount;

    public String status;

    @JsonProperty("gmt_create")
    public String gmtCreate;

    @JsonProperty("gmt_trans")
    public String gmtTrans;

    @JsonProperty("payer_logon_id")
    public String payerLogonId;

    @JsonProperty("payer_user_id")
    public String payerUserId;

    @JsonProperty("payer_open_id")
    public String payerOpenId;

    @JsonProperty("merchant_app_id")
    public String merchantAppId;

    @JsonProperty("payee_logon_id")
    public String payeeLogonId;

    @JsonProperty("payee_user_id")
    public String payeeUserId;

    @JsonProperty("total_freeze_amount")
    public String totalFreezeAmount;

    @JsonProperty("total_unfreeze_amount")
    public String totalUnfreezeAmount;

    @JsonProperty("total_pay_amount")
    public String totalPayAmount;

    @JsonProperty("rest_amount")
    public String restAmount;

    @JsonProperty("credit_amount")
    public String creditAmount;

    @JsonProperty("fund_amount")
    public String fundAmount;

    @JsonProperty("total_freeze_credit_amount")
    public String totalFreezeCreditAmount;

    @JsonProperty("total_freeze_fund_amount")
    public String totalFreezeFundAmount;

    @JsonProperty("total_unfreeze_credit_amount")
    public String totalUnfreezeCreditAmount;

    @JsonProperty("total_unfreeze_fund_amount")
    public String totalUnfreezeFundAmount;

    @JsonProperty("total_pay_credit_amount")
    public String totalPayCreditAmount;

    @JsonProperty("total_pay_fund_amount")
    public String totalPayFundAmount;

    @JsonProperty("rest_credit_amount")
    public String restCreditAmount;

    @JsonProperty("rest_fund_amount")
    public String restFundAmount;

    @JsonProperty("pre_auth_type")
    public String preAuthType;

    @JsonProperty("trans_currency")
    public String transCurrency;

    @JsonProperty("credit_merchant_ext")
    public String creditMerchantExt;

    @JsonProperty("enterprise_pay_info")
    public String enterprisePayInfo;
}
