package com.lanyang.framework.pay.domain.credit.ali;

import com.lanyang.framework.pay.enums.ali.AuthConfirmMode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lanyang
 * @date 22025/12/15
 * @des
 */
@ApiModel(description = "交易扣款模型")
@Data
public class AliTradeDeductModel implements Serializable {

    private static final long serialVersionUID = 4288492628162924341L;

    /**
     * 商户订单号
     * 由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。
     */
    @ApiModelProperty(value = "商户订单号", example = "out_trade_no20240701010101001", required = true)
    private String outTradeNo;

    /**
     * 订单总金额
     * 单位为元，精确到小数点后两位，取值范围：[0.01,100000000]。
     */
    @ApiModelProperty(value = "订单总金额", example = "12.34", required = true)
    private String totalAmount;

    /**
     * 订单标题
     * 注意：不可使用特殊字符，如 /，=，& 等。
     */
    @ApiModelProperty(value = "订单标题", example = "XX租车租金", required = true)
    private String subject;

    /**
     * 商家和支付宝签约的产品码
     * 芝麻免押产品固定传PREAUTH_PAY
     */
    @ApiModelProperty(value = "签约产品码", example = "PREAUTH_PAY", required = true)
    private String productCode;

    /**
     * 资金预授权单号
     * 支付宝预授权和新当面资金授权场景下必填
     */
    @ApiModelProperty(value = "资金预授权单号", example = "2016110310002001760201905725", required = false)
    private String authNo;

    /**
     * 预授权确认模式
     * 适用于支付宝预授权和新当面资金授权场景。枚举值：
     * COMPLETE：转交易完成后解冻剩余冻结金额；
     * NOT_COMPLETE：转交易完成后不解冻剩余冻结金额；
     * 默认值为NOT_COMPLETE。
     */
    @ApiModelProperty(value = "预授权确认模式", example = "NOT_COMPLETE", required = false)
    private AuthConfirmMode authConfirmMode;

    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;

    @ApiModelProperty(value = "用户openId")
    private String openId;

}