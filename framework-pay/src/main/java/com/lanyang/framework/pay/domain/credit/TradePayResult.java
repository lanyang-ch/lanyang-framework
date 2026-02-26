package com.lanyang.framework.pay.domain.credit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ApiModel("扣款交易查询结果")
@Data
public class TradePayResult {

    @ApiModelProperty("交易结果")
    private String tradeStatus;

    @ApiModelProperty("交易订单号")
    private String orderNo;

    @ApiModelProperty("支付宝/微信交易订单号")
    private String tradeNo;

    @ApiModelProperty("订单金额")
    private String totalAmount;

    @ApiModelProperty("错误编码")
    private String errorCode;

    @ApiModelProperty("错误信息")
    private String errorMsg;
}
