package com.lanyang.framework.pay.config.ali;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ConfigurationProperties(prefix = "alipay.pay")
@Component
@Data
public class AliPayInitProperties {

    /** 服务号的应用ID */
    private String appId;

    /** 私钥 */
    private String privateKey;

    /** 公钥 */
    private String publicKey;

    /** 支付宝服务地址 */
    private String serverUrl;

    /** 回调服务域名*/
    private String notifyServer;

}

