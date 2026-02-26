package com.lanyang.framework.pay.enums;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum CommonTradeStatus {

    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    ;

    private final String code;
    private final String desc;

    CommonTradeStatus(String code, String desc) {
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
