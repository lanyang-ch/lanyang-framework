package com.lanyang.framework.pay.domain.credit.ali;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ApiModel("资金授权解冻参数")
@Data
public class AliCreditOrderUnfreezeModel {

    @ApiModelProperty(value = "解冻请求流水号。 如果是针对同一笔授权单不同的解冻请求，如第一次解冻1元，第二次解冻2元，则解冻请求流水号必须不重复； 如果是针对同一笔解冻请求的多次发起，则需要保证每次发起，解冻请求流水号和解冻金额都相同", required = true)
    private String outRequestNo;

    @ApiModelProperty(value = "支付宝授权资金订单号", required = true)
    private String authNo;

    @ApiModelProperty(value = "本次操作解冻的金额，单位为：元（人民币），精确到小数点后两位", required = true)
    private String amount;

    @ApiModelProperty(value = "商户对本次解冻操作的附言描述", required = true)
    private String remark;

    @ApiModelProperty("回调地址")
    private String notifyUrl;
}
