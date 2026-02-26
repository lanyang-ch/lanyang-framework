package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
public enum RefundQueryOptions {

    REFUND_DETAIL_ITEM_LIST("refund_detail_item_list", "本次退款使用的资金渠道"),
    GMT_REFUND_PAY("gmt_refund_pay", "退款执行成功的时间"),
    DEPOSIT_BACK_INFO("voucher_detail_list", "银行卡冲退信息");

    private final String code;

    private final String desc;

    RefundQueryOptions(String value, String desc) {
        this.code = value;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
