package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
public enum QueryOptions {

    TRADE_SETTLE_INFO("trade_settle_info", "交易结算信息"),
    FUND_BILL_LIST("fund_bill_list", "交易支付使用的资金渠道"),
    VOUCHER_DETAIL_LIST("voucher_detail_list", "交易支付时使用的所有优惠券信息"),
    DISCOUNT_GOODS_DETAIL("discount_goods_detail", "交易支付使用单品券优惠的商品优惠信息"),
    MDISCOUNT_AMOUNT("mdiscount_amount", "商家优惠金额"),
    MEDICAL_INSURANCE_INFO("medical_insurance_info", "医保信息");

    private final String code;

    private final String desc;

    QueryOptions(String value, String desc) {
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
