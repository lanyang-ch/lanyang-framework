package com.lanyang.framework.pay.domain.credit.ali;

import com.lanyang.framework.pay.domain.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/10/21
 * @des
 */
@ApiModel("资金授权解冻响应")
@Data
public class AliDepositUnfreezeResult extends BaseResult {

    @ApiModelProperty("本次操作解冻的金额")
    private String amount;

    @ApiModelProperty("支付宝资金授权订单号")
    private String authNo;

    @ApiModelProperty("本次解冻操作中信用解冻金额")
    private String creditAmount;

    @ApiModelProperty("本次解冻操作中自有资金解冻金额")
    private String fundAmount;

    @ApiModelProperty("授权资金解冻成功时间")
    private String gmtTrans;

    @ApiModelProperty("支付宝资金操作流水号")
    private String operationId;

    @ApiModelProperty("商户的授权资金订单号")
    private String outOrderNo;

    @ApiModelProperty("商户本次资金操作的请求流水号")
    private String outRequestNo;

    @ApiModelProperty("资金操作流水的状态 目前支持：SUCCESS：成功")
    private String status;
}
