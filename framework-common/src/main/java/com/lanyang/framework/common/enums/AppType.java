package com.lanyang.framework.common.enums;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public enum AppType {

    WECHAT("MP-WEIXIN", "微信"),
    ALIPAY("MP-ALIPAY", "支付宝"),
    ;

    private String code;

    private String desc;

    AppType(String code, String desc) {
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
