package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.jsapi.QueryOrderResult;
import com.wechat.pay.java.service.payments.model.PromotionDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("微信查询订单结果")
@Data
public class WechatQueryOrderResult extends QueryOrderResult {

    @ApiModelProperty("微信appId")
    private String appId;

    @ApiModelProperty("微信商户号")
    private String mchId;

    @ApiModelProperty("当前订单的交易类型")
    private String tradeType;

    @ApiModelProperty("微信交易状态描述")
    private String tradeStateDesc;

    @ApiModelProperty("商户下单时传入的自定义数据包")
    private String attach;

    @ApiModelProperty(value = "用户完成订单支付的时间。该参数在订单支付成功后返回", example = "2015-05-20T13:29:35+08:00 表示北京时间2015年5月20日13点29分35秒")
    private String successTime;

    @ApiModelProperty("用户实际支付金额")
    private BigDecimal payerTotal;

    @ApiModelProperty("代金券信息，当订单支付时，有使用代金券时，该字段将返回所使用的代金券信息")
    private List<PromotionDetail> promotionDetail;
}
