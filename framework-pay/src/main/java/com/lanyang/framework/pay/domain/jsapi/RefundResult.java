package com.lanyang.framework.pay.domain.jsapi;

import com.lanyang.framework.pay.domain.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
@ApiModel("退款结果")
public class RefundResult extends BaseResult {

    @ApiModelProperty("商户订单号，支付订单号")
    private String outTradeNo;

    @ApiModelProperty("微信或者支付宝的交易订单号")
    private String tradeNo;

    @ApiModelProperty("商户退款单号")
    private String outRefundNo;

    /** fund_change=Y为退款成功，fund_change=N或无此字段值返回时需通过退款查询接口进一步确认退款状态 */
    private String fundChange;
}
