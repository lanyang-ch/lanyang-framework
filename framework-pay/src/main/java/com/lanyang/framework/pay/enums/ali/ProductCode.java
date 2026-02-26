package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des 销售产品码
 */
public enum ProductCode {

    PRE_AUTH_ONLINE("PRE_AUTH_ONLINE", "支付宝预授权产品固定为 PRE_AUTH_ONLINE"),
    PREAUTH_PAY("PREAUTH_PAY", "商家和支付宝签约的产品码，固定传PREAUTH_PAY"),
    JSAPI_PAY("JSAPI_PAY", "JSAPI_PAY"),
    ;

    private final String code;

    private final String desc;

    ProductCode(String code, String desc) {
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
