package com.lanyang.framework.pay.domain;

import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @date 2025/12/17
 * @author lanyang
 * @des
 */
@ApiModel("支付请求基础参数")
public class BaseParam {

    @ApiModelProperty("支付方式")
    private final PayType payType;

    public BaseParam(PayType payType) {
        // 非空校验
        if (payType == null) {
            throw new IllegalArgumentException("支付方式不能为空");
        }
        this.payType = payType;
    }

    public PayType getPayType() {
        return payType;
    }
}
