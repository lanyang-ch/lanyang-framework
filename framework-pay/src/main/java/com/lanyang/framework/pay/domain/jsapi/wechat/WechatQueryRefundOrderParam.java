package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.QueryRefundOrderParam;
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
@ApiModel("微信查询退款订单参数")
@Getter
@Setter
public class WechatQueryRefundOrderParam extends BaseParam implements QueryRefundOrderParam {


    @ApiModelProperty(value = "商户退款单号", required = true)
    private String outRefundNo;

    public WechatQueryRefundOrderParam() {
        super(PayType.WECHAT);
    }
}
