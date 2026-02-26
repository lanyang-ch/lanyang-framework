package com.lanyang.framework.pay.domain.jsapi.ali;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
public class AliRefundNotify {

    /**
     * 通知时间。通知的发送时间。格式为 yyyy-MM-dd HH:mm:ss。
     */
    private Date notify_time;

    /**
     * 通知类型。枚举值：trade_status_sync。
     */
    private String notify_type;

    /**
     * 通知校验 ID。
     */
    private String notify_id;

    /**
     * 签名类型。商家生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA。
     */
    private String sign_type;

    /**
     * 签名。用于验证通知的真实性。
     */
    private String sign;

    /**
     * 支付宝交易号。支付宝交易凭证号。
     */
    private String trade_no;

    /**
     * 开发者的 app_id。支付宝分配给开发者的应用 APPID。
     */
    private String app_id;

    /**
     * 开发者的 app_id，在服务商调用的场景下为授权方的 app_id。
     */
    private String auth_app_id;

    /**
     * 商户订单号。
     */
    private String out_trade_no;

    /**
     * 商家业务号。商家业务 ID，主要是退款通知中返回退款申请的流水号。
     */
    private String out_biz_no;

    /**
     * 买家支付宝用户号。买家支付宝账号对应的支付宝唯一用户号。
     */
    private String buyer_id;

    /**
     * 买家支付宝账号。
     */
    private String buyer_logon_id;

    /**
     * 卖家支付宝用户号。
     */
    private String seller_id;

    /**
     * 卖家支付宝账号。
     */
    private String seller_email;

    /**
     * 交易状态。咨询目前所处的状态。
     */
    private String trade_status;

    /**
     * 订单金额。本次交易支付的订单金额，单位为人民币（元）。支持小数点后两位。
     */
    private BigDecimal total_amount;

    /**
     * 实收金额。商家在交易中实际收到的款项，单位为人民币（元）。
     */
    private BigDecimal receipt_amount;

    /**
     * 开票金额。用户在交易中支付的可开发票的金额。支持小数点后两位。
     */
    private BigDecimal invoice_amount;

    /**
     * 付款金额。用户在交易中支付的金额。支持小数点后两位。
     */
    private BigDecimal buyer_pay_amount;

    /**
     * 集分宝金额。使用集分宝支付的金额。支持小数点后两位。
     */
    private BigDecimal point_amount;

    /**
     * 总退款金额。退款通知中，返回总退款金额，单位为元，支持小数点后两位。
     */
    private BigDecimal refund_fee;

    /**
     * 实际退款金额。商家实际退款给用户的金额，单位为元，支持小数点后两位。
     */
    private BigDecimal send_back_fee;

    /**
     * 订单标题。商品的标题/交易标题/订单标题/订单关键字等。
     */
    private String subject;

    /**
     * 商品描述。该订单的备注、描述、明细等。
     */
    private String body;

    /**
     * 交易创建时间。该笔交易创建的时间。格式为 yyyy-MM-dd HH:mm:ss。
     */
    private Date gmt_create;

    /**
     * 交易付款时间。该笔交易的买家付款时间。格式为 yyyy-MM-dd HH:mm:ss。
     */
    private Date gmt_payment;

    /**
     * 交易退款时间。该笔交易的退款时间。格式为 yyyy-MM-dd HH:mm:ss.SS。
     */
    private Date gmt_refund;

    /**
     * 交易结束时间。该笔交易结束时间。格式为 yyyy-MM-dd HH:mm:ss。
     */
    private Date gmt_close;

    /**
     * 支付金额信息。支付成功的各个渠道金额信息。
     */
    private List<FundBill> fund_bill_list;

    /**
     * 优惠券信息。本交易支付时所使用的所有优惠券信息。
     */
    private List<VoucherDetail> voucher_detail_list;

    /**
     * 账期结算标识，指已完成支付的订单会进行账期管控，不会实时结算。
     */
    private String biz_settle_mode;

    /**
     * 资金明细信息内部类
     */
    @Data
    public static class FundBill {
        /**
         * 金额
         */
        private String amount;
        /**
         * 资金渠道
         */
        private String fundChannel;

    }

    /**
     * 优惠券信息内部类
     */
    @Data
    public static class VoucherDetail {

        private String voucherId;
        private String templateId;

        /**
         * 优惠券名称
         */
        private String name;

        /**
         * 金额
         */
        private String amount;
        /**
         * 商家出资
         */
        private String merchantContribute;

        /**
         * 其他出资
         */
        private String otherContribute;
        /**
         * 优惠券类型
         */
        private String type;
        /**
         * 备注信息
         */
        private String memo;

    }

}
