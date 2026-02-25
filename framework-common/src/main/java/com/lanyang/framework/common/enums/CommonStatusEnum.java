package com.lanyang.framework.common.enums;

/**
 * @author lanyang
 * @date 2025/6/20
 * @des
 */
public enum CommonStatusEnum {
    OK(1, "正常"),

    DISABLED(0, "停用"),

    ;

    private final Integer code;

    private final String desc;

    CommonStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
