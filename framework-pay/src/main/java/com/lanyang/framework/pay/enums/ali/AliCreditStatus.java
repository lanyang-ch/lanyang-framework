package com.lanyang.framework.pay.enums.ali;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum AliCreditStatus {
    INIT("INIT", "初始状态：已创建未授权"),
    AUTHORIZED("AUTHORIZED", "已授权状态：授权成功，可以进行转支付或解冻操作"),
    FINISH("FINISH", "完成状态：转支付完成且无剩余冻结资金"),
    CLOSED("CLOSED", "关闭状态：授权未完成超时关闭或冻结资金全额解冻"),
    FAIL("FAIL", "授权失败: 非支付宝通知结果"),
    AUTH_ORDER_NOT_EXIST("AUTH_ORDER_NOT_EXIST", "支付宝资金授权订单不存在"),
    ;

    private final String code;
    private final String desc;

    AliCreditStatus(String code, String desc) {
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
