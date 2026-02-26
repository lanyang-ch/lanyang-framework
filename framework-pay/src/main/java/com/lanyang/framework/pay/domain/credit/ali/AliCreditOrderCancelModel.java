package com.lanyang.framework.pay.domain.credit.ali;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ApiModel("取消免押订单参数")
@Data
public class AliCreditOrderCancelModel {

    @ApiModelProperty("商户对本次撤销操作的附言描述。 长度不超过100个字母或50个汉字")
    private String remark;

    @ApiModelProperty("支付宝的授权资金操作流水号。 与商户的授权资金操作流水号不能同时为空，二者都传入时，以支付宝的授权资金操作流水号为准，该参数与支付宝授权资金订单号配对使用")
    private String operationId;

    @ApiModelProperty("商户的授权资金操作流水号。 与支付宝的授权资金操作流水号不能同时为空，二者都传入时，以支付宝的授权资金操作流水号为准，该参数与商户的授权资金订单号配对使用。 该值与资金冻结时out_request_no一致")
    private String outRequestNo;

    @ApiModelProperty("支付宝授权资金订单号。 与商户的授权资金订单号不能同时为空，二者都传入时，以支付宝资金授权订单号为准，该参数与支付宝授权资金操作流水号配对使用")
    private String authNo;

    @ApiModelProperty("商户的授权资金订单号。 与支付宝的授权资金订单号不能同时为空，二者都传入时，以支付宝的授权资金订单号为准，该参数与商户的授权资金操作流水号配对使用。 该值与资金冻结时 out_order_no一致")
    private String outOrderNo;

    @ApiModelProperty("支付宝服务器主动通知商户服务器里指定的页面http/https路径。在body参数中传递")
    private String notifyUrl;
}
