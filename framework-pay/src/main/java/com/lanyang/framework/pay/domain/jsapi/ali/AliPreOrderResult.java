package com.lanyang.framework.pay.domain.jsapi.ali;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lanyang.framework.pay.domain.jsapi.PreOrderResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
@ApiModel("支付宝预下单结果")
public class AliPreOrderResult extends PreOrderResult {

    @JsonProperty("trade_no")
    @ApiModelProperty("支付宝交易号")
    private String tradeNo;
}
