package com.lanyang.framework.pay.enums;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum PayType {

    WECHAT("MP-WEIXIN", "1","微信"),
    ALIPAY("MP-ALIPAY", "2", "支付宝"),
    ;

    private String code;

    private String dictCode;

    private String desc;

    PayType(String code, String dictCode, String desc) {
        this.code = code;
        this.dictCode = dictCode;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDictCode() {
        return dictCode;
    }

    public String getDesc() {
        return desc;
    }

    public static PayType getTypeByCode(String code){
        for (PayType value : values()) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static PayType getTypeByDictCode(String dictCode){
        for (PayType value : values()) {
            if(value.getDictCode().equals(dictCode)){
                return value;
            }
        }
        return null;
    }
}
