package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.CreateOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Getter
@Setter
@ApiModel("微信创建订单参数")
public class WechatCreateOrderParam extends BaseParam implements CreateOrderParam {

    /** 微信/支付宝openId */
    private String openId;

    /** 商户订单号 */
    private String outTradeNo;

    /** 商品描述 */
    private String body;

    /** 订单总金额，单位为元 */
    private BigDecimal totalFee;

    /** 回调地址 */
    private String notifyUrl;

    /** 订单相对超时时间 */
    private String timeoutExpress;

    public WechatCreateOrderParam() {
        super(PayType.WECHAT);
    }
}
