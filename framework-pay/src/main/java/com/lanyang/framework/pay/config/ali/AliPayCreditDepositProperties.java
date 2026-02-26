package com.lanyang.framework.pay.config.ali;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ConfigurationProperties(prefix = "alipay.credit")
@Data
public class AliPayCreditDepositProperties {

    /** 信用服务id */
    private String serviceId;

    /** 信用授权类目*/
    private String category;

    /** 创建免押订单(线上资金授权冻结接口)结果回调通知地址 */
    private String freezeNotifyUrl;

    /** 完结免押订单(资金授权解冻接口)结果回调通知地址*/
    private String unfreezeNotifyUrl;

    /** 取消免押订单(资金授权撤销接口)结果回调通知地址*/
    private String cancelFreezeNotifyUrl;

    /** 免押扣款(统一收单交易支付接口)结果回调通知地址*/
    private String payNotifyUrl;

    /** 免押退款(统一收单交易退款接口)结果回调通知地址*/
    private String refundNotifyUrl;


}
