package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des 需要查询的授权资金操作类型
 */
public enum OperationType {

    FREEZE("FREEZE", "冻结"),
    UNFREEZE("UNFREEZE", "解冻"),
    PAY("PAY", "支付"),
    ;

    private final String code;

    private final String desc;

    OperationType(String code, String desc) {
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
