package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderParam;
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
@Getter
@Setter
@ApiModel("微信查询订单参数")
public class WechatQueryOrderParam extends BaseParam implements QueryOrderParam {

    @ApiModelProperty(value = "商户订单号", required = true)
    private String outTradeNo;

    @ApiModelProperty("微信商户号,不需要传,会通过配置读取")
    private String mchId;

    public WechatQueryOrderParam() {
        super(PayType.WECHAT);
    }
}
