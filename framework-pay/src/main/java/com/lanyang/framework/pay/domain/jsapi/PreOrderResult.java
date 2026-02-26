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
@ApiModel("预下单结果")
public class PreOrderResult extends BaseResult {

    @ApiModelProperty("商户订单号")
    private String outTradeNo;
}
