package com.lanyang.framework.pay.enums.wechat;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum WechatTransferStatus {

    ACCEPTED("ACCEPTED", "转账已受理，可原单重试（非终态）"),
    PROCESSING("PROCESSING", "转账锁定资金中。如果一直停留在该状态，建议检查账户余额是否足够，如余额不足，可充值后再原单重试（非终态）"),
    WAIT_USER_CONFIRM("WAIT_USER_CONFIRM", "待收款用户确认，当前转账单据资金已锁定，可拉起微信收款确认页面进行收款确认（非终态）"),
    TRANSFERING("TRANSFERING", "转账中，可拉起微信收款确认页面再次重试确认收款（非终态）"),
    SUCCESS("SUCCESS", "转账成功，表示转账单据已成功（终态）"),
    FAIL("FAIL", "转账失败，表示该笔转账单据已失败。若需重新向用户转账，请重新生成单据并再次发起（终态）"),
    CANCELING("CANCELING", "转账撤销中，商户撤销请求受理成功，该笔转账正在撤销中，需查单确认撤销的转账单据状态（非终态）"),
    CANCELLED("CANCELLED","转账撤销完成，代表转账单据已撤销成功（终态）");

    private String code;

    private String desc;

    WechatTransferStatus(String code, String desc) {
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
