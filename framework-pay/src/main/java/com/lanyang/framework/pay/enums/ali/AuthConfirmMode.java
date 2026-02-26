package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/10/10
 * @des 预授权确认模式
 */
public enum AuthConfirmMode {

    COMPLETE("COMPLETE", "交易完成后解冻剩余冻结金额"),
    NOT_COMPLETE("NOT_COMPLETE", "交易完成后不解冻剩余冻结金额"),
    ;

    private final String code;

    private final String desc;

    AuthConfirmMode(String code, String desc) {
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
