package com.lanyang.framework.common.enums;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public enum DeleteStatusEnum {
    EXIST(0, "存在"),
    DELETED(1, "删除");

    private final int code;
    private final String description;

    DeleteStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
