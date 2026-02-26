package com.lanyang.framework.pay.domain.jsapi.ali;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.CloseOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("支付宝关闭订单参数")
@Getter
@Setter
public class AliCloseOrderParam extends BaseParam implements CloseOrderParam {

    @ApiModelProperty("商户订单号,和 tradeNo 二选一必填")
    private String outTradeNo;

    @ApiModelProperty("支付宝订单号,和 outTradeNo 二选一必填")
    private String tradeNo;

    public AliCloseOrderParam() {
        super(PayType.ALIPAY);
    }
}
