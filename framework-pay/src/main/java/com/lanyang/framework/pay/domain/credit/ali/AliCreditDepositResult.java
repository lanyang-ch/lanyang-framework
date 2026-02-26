package com.lanyang.framework.pay.domain.credit.ali;

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
@ApiModel("信用免押响应")
public class AliCreditDepositResult extends BaseResult {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("支付宝/微信生成的字符串，用于调起支付")
    private String orderStr;
}
