package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.RefundOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("微信退款订单参数")
@Getter
@Setter
public class WechatRefundOrderParam extends BaseParam implements RefundOrderParam {

    @ApiModelProperty("商户订单号, 与tradeNo二选一")
    private String outTradeNo;

    @ApiModelProperty("微信订单号, 与outTradeNo二选一")
    private String tradeNo;

    @ApiModelProperty(value = "商户退款单号", required = true)
    private String outRefundNo;

    @ApiModelProperty("退款原因")
    private String reason;

    @ApiModelProperty(value = "回调地址", required = true)
    private String notifyUrl;

    @ApiModelProperty(value = "订单金额", required = true)
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "退款金额", required = true)
    private BigDecimal refundAmount;


    public WechatRefundOrderParam() {
        super(PayType.WECHAT);
    }
}
