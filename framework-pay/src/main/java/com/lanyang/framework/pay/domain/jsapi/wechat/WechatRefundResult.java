package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.lanyang.framework.pay.domain.jsapi.RefundResult;
import com.wechat.pay.java.service.refund.model.Amount;
import com.wechat.pay.java.service.refund.model.Channel;
import com.wechat.pay.java.service.refund.model.FundsAccount;
import com.wechat.pay.java.service.refund.model.Promotion;
import com.wechat.pay.java.service.refund.model.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des 完全复用了微信退款结果
 */
@ApiModel("微信退款结果")
@Data
public class WechatRefundResult extends RefundResult {

    @ApiModelProperty(value = "退款单在微信支付侧生成的唯一标识", required = true)
    private String refundId;

    @ApiModelProperty(value = "商户退款单号", required = true)
    private String outRefundNo;

    @ApiModelProperty(value = "微信支付订单号", required = true)
    private String transactionId;

    @ApiModelProperty(value = "商户订单号", required = true)
    private String outTradeNo;

    @ApiModelProperty(value = "当前退款单的退款入账方", required = true)
    private String userReceivedAccount;

    @ApiModelProperty(value = "退款成功时间")
    private String successTime;

    @ApiModelProperty(value = "退款创建时间", required = true)
    private String createTime;

    @ApiModelProperty(value = "退款状态", required = true)
    private Status status;

    @ApiModelProperty(value = "退款所使用资金对应的资金账户类型", required = true)
    private FundsAccount fundsAccount;

    @ApiModelProperty(value = "退款金额信息", required = true)
    private Amount amount;

    @ApiModelProperty(value = "优惠退款信息,订单各个代金券的退款详情，订单使用了代金券且代金券发生退款时返回")
    private List<Promotion> promotionDetail;

    @ApiModelProperty(value = "退款渠道", required = true)
    private Channel channel;
}
