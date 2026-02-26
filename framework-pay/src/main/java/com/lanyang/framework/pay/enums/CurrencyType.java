package com.lanyang.framework.pay.enums;

/**
 * @author lanyang
 * @date 2025/12/16
 * @des
 */
public enum CurrencyType {
    CNY("CNY", "人民币"),
    USD("USD", "美元"),
    JPY("JPY", "日元"),
    EUR("EUR", "欧元"),
    GBP("GBP", "英镑"),
    AUD("AUD", "澳元"),
    CAD("CAD", "加元"),
    CHF("CHF", "瑞士法郎"),
    HKD("HKD", "港币");
    private String code;
    private String name;
    CurrencyType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
