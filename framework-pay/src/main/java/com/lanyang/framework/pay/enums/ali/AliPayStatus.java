package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum AliPayStatus {
    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;
    private final String desc;

    AliPayStatus(String code, String desc) {
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
