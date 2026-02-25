package com.lanyang.framework.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public enum LoginType {

    WECHAT("MP-WEIXIN", "微信登录"),
    ALIPAY("MP-ALIPAY", "支付宝登录"),
    ;

    private String code;

    private String desc;

    LoginType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public  static LoginType getTypeByCode(String code){
        for (LoginType value : values()) {
            if(StringUtils.equals(value.getCode(), code)){
                return value;
            }
        }
        return null;
    }
}
