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
@ApiModel("免押订单查询结果")
public class AliCreditQueryResult extends BaseResult {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("支付宝资金授权订单号")
    private String authNo;

    @ApiModelProperty("支付宝资金操作流水号")
    private String operationId;

    @ApiModelProperty("SUCCESS：成功， FAIL：失败")
    private String status;

}
