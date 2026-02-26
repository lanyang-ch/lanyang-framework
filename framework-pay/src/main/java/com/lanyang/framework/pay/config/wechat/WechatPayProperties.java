package com.lanyang.framework.pay.config.wechat;

import com.lanyang.framework.pay.util.WXPayUtility;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ConfigurationProperties(prefix = "wechat.pay")
@Component
@Data
public class WechatPayProperties {

    /** 微信公众号id或小程序id */
    private String appId;

    /** 微信服务地址 */
    private String host;

    /** 商户号 */
    private String mchId;

    /** 微信支付证书序列号 */
    private String certificateSerialNo;

    /** 私钥证书路径*/
    private String privateKeyPath;

    private PrivateKey privateKey;

    /** 公钥id */
    private String wechatPayPublicKeyId;

    /** 公钥证书路径*/
    private String publicKeyPath;

    private PublicKey publicKey;

    /** 商户解密APIv3的回调通知使用 */
    private String apiV3Key;


    @PostConstruct
    public void init() {
        try {
            // 调用工具类加载私钥（确保 WXPayUtility 类及方法可正常访问）
            privateKey = WXPayUtility.loadPrivateKeyFromPath(getPrivateKeyPath());
            publicKey = WXPayUtility.loadPublicKeyFromPath(getPublicKeyPath());
        } catch (Exception e) {
            // 初始化失败时抛出异常，阻止项目启动（避免后续使用时出现空指针）
            throw new RuntimeException("初始化微信支付密钥失败，请检查密钥路径或文件是否正确", e);
        }
    }

}

