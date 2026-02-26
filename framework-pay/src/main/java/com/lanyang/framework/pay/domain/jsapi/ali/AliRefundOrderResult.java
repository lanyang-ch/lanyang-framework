package com.lanyang.framework.pay.domain.jsapi.ali;

import com.alipay.v3.model.DepositBackInfo;
import com.alipay.v3.model.TradeFundBill;
import com.alipay.v3.model.VoucherDetail;
import com.lanyang.framework.pay.domain.jsapi.RefundResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des <a href = "https://opendocs.alipay.com/open-v3/bd759aa8_alipay.trade.refund?scene=common&pathHash=87bdb4fa"></a>
 */
@ApiModel("支付宝退款结果")
@Data
public class AliRefundOrderResult extends RefundResult{

    @ApiModelProperty("支付宝交易号")
    private String tradeNo;

    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    @ApiModelProperty("商户退款单号")
    private String outRefundNo;

    @ApiModelProperty("订单总金额")
    private String totalAmount;

    @ApiModelProperty("退款金额")
    private String refundAmount;

    @ApiModelProperty(value = "退款状态", example = "REFUND_SUCCESS")
    private String refundStatus;

    @ApiModelProperty("退款使用的资金渠道。 只有在签约中指定需要返回资金明细，或者入参的query_options中指定时才返回该字段信息")
    private List<TradeFundBill> refundDetailItemList;

    @ApiModelProperty("退款时间。默认不返回该信息，需要在入参的query_options中指定gmt_refund_pay值时才返回该字段信息")
    private String gmtRefundPay;

    @ApiModelProperty("本次请求退惠营宝金额")
    private String refundHybAmount;

    @ApiModelProperty("本交易支付时使用的所有优惠券信息。 只有在query_options中指定refund_voucher_detail_list时才返回该字段信")
    private List<VoucherDetail> refundVoucherDetailList;

    @ApiModelProperty("次商户实际退回金额。单位：元。 说明：如需获取该值，需在入参query_options中传入 refund_detail_item_list")
    private String sendBackFee;

    @ApiModelProperty("交易在支付时候的门店名称")
    private String storeName;

    @ApiModelProperty("银行卡冲退信息； 默认不返回该信息，需要在入参的query_options中指定\"deposit_back_info\"值时才返回该字段信息金额")
    private List<DepositBackInfo> depositBackInfoList;

}
