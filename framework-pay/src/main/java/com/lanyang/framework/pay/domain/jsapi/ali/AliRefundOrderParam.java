package com.lanyang.framework.pay.domain.jsapi.ali;

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
 * @des <a href="https://opendocs.alipay.com/open-v3/bd759aa8_alipay.trade.refund?scene=common&pathHash=87bdb4fa"></a>
 */
@ApiModel("支付宝退款订单参数")
@Getter
@Setter
public class AliRefundOrderParam extends BaseParam implements RefundOrderParam {

    @ApiModelProperty("商户订单号, 与tradeNo二选一")
    private String outTradeNo;

    @ApiModelProperty("支付宝订单号, 与outTradeNo二选一")
    private String tradeNo;

    @ApiModelProperty("商户退款单号, 退款请求号。 标识一次退款请求，需要保证在交易号下唯一，如需部分退款，则此参数必传")
    private String outRefundNo;

    @ApiModelProperty("退款原因")
    private String reason;

    @ApiModelProperty(value = "回调地址", required = true)
    private String notifyUrl;

    @ApiModelProperty("订单金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "退款金额", required = true)
    private BigDecimal refundAmount;

    public AliRefundOrderParam() {
        super(PayType.ALIPAY);
    }
}
