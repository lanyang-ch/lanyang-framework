package com.lanyang.framework.pay.domain.credit.ali;

import com.lanyang.framework.pay.enums.ali.DepositProductMode;
import com.lanyang.framework.pay.enums.ali.ProductCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lanyang
 * 创建芝麻免押订单时必需的入参
 * 1、详细内容可参考接口文档：
 *
 * @see <a
 * href="https://opendocs.alipay.com/open/e2e70da4_alipay.fund.auth.order.app
 * .freeze?pathHash=1002681c&ref=api&scene=3392871eb88945fa977f613f214ea1c6/">
 * alipay.fund.auth.order.app.freeze(线上资金授权冻结接口)</a>
 */
@ApiModel(description = "信用单模型")
@Data
public class AliCreditOrderModel implements Serializable {

    private static final long serialVersionUID = 89765465475L;

    /**
     * 必填：预授权支付产品码
     * 如： PREAUTH_PAY
     */
    @ApiModelProperty(value = "预授权支付产品码", example = "PREAUTH_PAY", required = true)
    private ProductCode productCode;

    /**
     * 必填：预授权冻结总金额
     * 如： 100.00
     */
    @ApiModelProperty(value = "预授权冻结总金额", example = "12.34", required = true)
    private String totalAmount;

    /**
     * 必填：商户授权资金订单号
     * 如： outRequestNo-8077735255938023
     */
    @ApiModelProperty(value = "商户授权资金订单号", example = "outRequestNo-8077735255938023", required = true)
    private String outRequestNo;

    /**
     * 必填：商户本次资金操作的请求流水号
     * 如： 8077735255938032
     */
    @ApiModelProperty(value = "商户本次资金操作的请求流水号", example = "outOrderNo-8077735255938023", required = true)
    private String outOrderNo;

    /**
     * 必填：订单标题
     * 如：XX租车押金
     */
    @ApiModelProperty(value = "订单标题", example = "XX租车押金", required = true)
    private String orderTitle;

    /**
     * 必填：芝麻免押模式
     * 1、后付金额已知: POSTPAY
     * 2、后付金额未知: POSTPAY_UNCERTAIN
     * 3、纯免押: DEPOSIT_ONLY
     * 参考说明：@see <a href="https://opendocs.alipay.com/b/08tf3t?pathHash=d67d7545">...</a>
     */
    @ApiModelProperty(value = "芝麻免押模式", example = "POSTPAY", required = true)
    private DepositProductMode depositProductMode;

    /**
     * 特殊可选：免押模式为 POSTPAY 或 POSTPAY_UNCERTAIN 需要必填
     * 如：详细见 @link{ com.alipay.zmdepositdemo.model.PostPaymentsModel }
     */
    @ApiModelProperty(value = "付费项目", example = "", required = false)
    private AliPostPaymentsModel postPaymentsModel;

    /**
     * 必填：预授权类目和信用服务ID，
     * 如：{"category":"CHARGE_PILE_CAR","serviceId":"2020042800000000000001450466"}
     */
    @ApiModelProperty(value = "预授权类目和信用服务ID",
            example = "{\"category\":\"CHARGE_PILE_CAR\",\"serviceId\":\"2020042800000000000001450466\"}", required = true)
    private String creditServiceStr;

    /**
     * 必填：预授权类目
     * 如：{"category":"CHARGE_PILE_CAR","serviceId":"2020042800000000000001450466"}
     */
    @ApiModelProperty(value = "预授权类目",
            example = "CHARGE_PILE_CAR", required = true)
    private String category;

    /**
     * 必填：信用服务ID，
     * 如：{"category":"CHARGE_PILE_CAR","serviceId":"2020042800000000000001450466"}
     */
    @ApiModelProperty(value = "信用服务ID",
            example = "2020042800000000000001450466", required = true)
    private String serviceId;

    /**
     * 可选：商户可用该参数指定支付渠道。 设置无特殊需要请勿传入
     * 如：[{\"payChannelType\":\"PCREDIT_PAY\"},{\"payChannelType\":\"MONEY_FUND\"}]
     */
    @ApiModelProperty(value = "预指定支付渠道", example = "[{\"payChannelType\":\"PCREDIT_PAY\"},{\"payChannelType\":\"MONEY_FUND\"}]",
            required = false)
    private String enablePayChannels;

    /**
     * 可选：买家实名信息,传入后支付宝会比对买家在支付宝端的实名信。 设置无特殊需要请勿传入
     * 如：{\"identity_hash\":\"acc2b92ffc5ed9b472faa19748f10045c30434132784f774b00216a56b8841c6\"}
     */
    @ApiModelProperty(value = "买家实名信息",
            example = "{\"identity_hash\":\"acc2b92ffc5ed9b472faa19748f10045c30434132784f774b00216a56b8841c6\"}", required = false)
    private String identityParams;

    /**
     * 可选：业务参数，如风控参数outRiskInfo等。
     * 如：{\"outRiskInfo\":\"{\"mcCreateTradeTime\":\"2022-03-11
     * 12:46:09\",\"extraAccountCertnoLastSix\":\"000011\",\"mobileOperatingPlatform\":\"ios\",\"sysVersion\":\"15.4.2\",
     * \"mcCreateTradeIp\":\"11.110.111.43\"}\"}
     */
    @ApiModelProperty(value = "业务参数",
            example = "{\"outRiskInfo\":\"{\"mcCreateTradeTime\":\"2022-03-11 12:46:09\",\"extraAccountCertnoLastSix\":\"000011\","
                    + "\"mobileOperatingPlatform\":\"ios\",\"sysVersion\":\"15.4.2\",\"mcCreateTradeIp\":\"11.110.111.43\"}\"}",
            required = false)
    private String businessParams;

    /**
     * 可选：预授权订单相对超时时间。从商户客户端请求时间开始计算。
     * 预授权订单允许的最晚授权时间，逾期将关闭该笔订单。取值范围：1m～15d。m-分钟，h-小时，d-天。 该参数数值不接受小数点， 如 1.5h，可转换为90m
     * 如：2d
     */
    @ApiModelProperty(value = "预授权订单相对超时时间", example = "2d", required = false)
    private String timeoutExpress;

    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;

    @ApiModelProperty(value = "授权令牌")
    private String accessToken;


}
