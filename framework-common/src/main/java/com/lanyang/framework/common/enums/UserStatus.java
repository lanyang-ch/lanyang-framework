package com.lanyang.framework.common.enums;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public enum UserStatus {
    OK(1, "正常"),
    DISABLE(0, "停用");

    private final int code;
    private final String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return desc;
    }
}
