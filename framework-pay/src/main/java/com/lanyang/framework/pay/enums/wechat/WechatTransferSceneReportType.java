package com.lanyang.framework.pay.enums.wechat;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public enum WechatTransferSceneReportType {

    ACTIVITY("活动名称"),
    AWARD("奖励说明");

    private String code;

    WechatTransferSceneReportType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
