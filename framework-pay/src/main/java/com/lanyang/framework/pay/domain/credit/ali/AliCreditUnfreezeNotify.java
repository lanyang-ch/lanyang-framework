package com.lanyang.framework.pay.domain.credit.ali;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
@ApiModel("资金授权解冻通知实体类")
public class AliCreditUnfreezeNotify {
    /**
     * 创建时间
     */
    @JsonProperty("gmt_create")
    private Date gmtCreate;

    /**
     * 字符集
     */
    @JsonProperty("charset")
    private String charset;

    /**
     * 剩余授信额度
     */
    @JsonProperty("rest_credit_amount")
    private BigDecimal restCreditAmount;

    /**
     * 操作类型
     */
    @JsonProperty("operation_type")
    private String operationType;

    /**
     * 签名
     */
    @JsonProperty("sign")
    private String sign;

    /**
     * 剩余资金金额
     */
    @JsonProperty("rest_fund_amount")
    private BigDecimal restFundAmount;

    /**
     * 授权单号
     */
    @JsonProperty("auth_no")
    private String authNo;

    /**
     * 通知ID
     */
    @JsonProperty("notify_id")
    private String notifyId;

    /**
     * 总冻结授信额度
     */
    @JsonProperty("total_freeze_credit_amount")
    private BigDecimal totalFreezeCreditAmount;

    /**
     * 通知类型
     */
    @JsonProperty("notify_type")
    private String notifyType;

    /**
     * 交易时间
     */
    @JsonProperty("gmt_trans")
    private Date gmtTrans;

    /**
     * 操作ID
     */
    @JsonProperty("operation_id")
    private String operationId;

    /**
     * 总支付资金金额
     */
    @JsonProperty("total_pay_fund_amount")
    private BigDecimal totalPayFundAmount;

    /**
     * 外部请求号
     */
    @JsonProperty("out_request_no")
    private String outRequestNo;

    /**
     * 应用ID
     */
    @JsonProperty("app_id")
    private String appId;

    /**
     * 签名类型
     */
    @JsonProperty("sign_type")
    private String signType;

    /**
     * 剩余金额
     */
    @JsonProperty("rest_amount")
    private BigDecimal restAmount;

    /**
     * 金额
     */
    @JsonProperty("amount")
    private BigDecimal amount;

    /**
     * 通知时间
     */
    @JsonProperty("notify_time")
    private Date notifyTime;

    /**
     * 资金金额
     */
    @JsonProperty("fund_amount")
    private BigDecimal fundAmount;

    /**
     * 总支付授信金额
     */
    @JsonProperty("total_pay_credit_amount")
    private BigDecimal totalPayCreditAmount;

    /**
     * 授信金额
     */
    @JsonProperty("credit_amount")
    private BigDecimal creditAmount;

    /**
     * 外部订单号
     */
    @JsonProperty("out_order_no")
    private String outOrderNo;

    /**
     * 总冻结资金金额
     */
    @JsonProperty("total_freeze_fund_amount")
    private BigDecimal totalFreezeFundAmount;

    /**
     * 版本号
     */
    @JsonProperty("version")
    private String version;

    /**
     * 总解冻资金金额
     */
    @JsonProperty("total_unfreeze_fund_amount")
    private BigDecimal totalUnfreezeFundAmount;

    /**
     * 总支付金额
     */
    @JsonProperty("total_pay_amount")
    private BigDecimal totalPayAmount;

    /**
     * 总冻结金额
     */
    @JsonProperty("total_freeze_amount")
    private BigDecimal totalFreezeAmount;

    /**
     * 总解冻授信金额
     */
    @JsonProperty("total_unfreeze_credit_amount")
    private BigDecimal totalUnfreezeCreditAmount;

    /**
     * 授权应用ID
     */
    @JsonProperty("auth_app_id")
    private String authAppId;

    /**
     * 总解冻金额
     */
    @JsonProperty("total_unfreeze_amount")
    private BigDecimal totalUnfreezeAmount;

    /**
     * 状态
     */
    @JsonProperty("status")
    private String status;

    /**
     * 付款方登录ID
     */
    @JsonProperty("payer_logon_id")
    private String payerLogonId;
}