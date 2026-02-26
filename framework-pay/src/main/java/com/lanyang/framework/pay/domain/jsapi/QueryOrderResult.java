package com.lanyang.framework.pay.domain.jsapi;

import com.lanyang.framework.pay.domain.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("查询订单结果")
@Data
public class QueryOrderResult extends BaseResult {

    @ApiModelProperty("支付宝/微信订单号")
    private String tradeNo;

    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    @ApiModelProperty("订单状态")
    private String tradeStatus;

    @ApiModelProperty("订单金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("买家openId")
    private String openId;
}
