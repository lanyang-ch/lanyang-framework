package com.lanyang.framework.pay.domain.jsapi.ali;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.CreateOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ApiModel("支付宝创建订单参数")
@Getter
@Setter
public class AliCreateOrderParam extends BaseParam implements CreateOrderParam {

    @ApiModelProperty(value = "支付宝openId", required = true)
    private String openId;

    @ApiModelProperty(value = "商户订单号", required = true)
    private String outTradeNo;

    @ApiModelProperty(value = "商品描述", required = true)
    private String body;

    @ApiModelProperty(value = "订单总金额，单位为元", required = true)
    private BigDecimal totalFee;

    @ApiModelProperty(value = "回调地址", required = true)
    private String notifyUrl;

    @ApiModelProperty(value = "订单相对超时时间")
    private String timeoutExpress;

    public AliCreateOrderParam() {
        super(PayType.ALIPAY);
    }

}
