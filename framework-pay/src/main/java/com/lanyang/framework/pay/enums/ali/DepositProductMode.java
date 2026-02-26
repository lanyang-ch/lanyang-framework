package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des 芝麻免押模式
 */
public enum DepositProductMode {

    POSTPAY("POSTPAY", "后付金额已知"),
    POSTPAY_UNCERTAIN("POSTPAY_UNCERTAIN", "后付金额未知"),
    DEPOSIT_ONLY("DEPOSIT_ONLY", "纯免押"),
    ;

    private final String code;

    private final String desc;

    DepositProductMode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
