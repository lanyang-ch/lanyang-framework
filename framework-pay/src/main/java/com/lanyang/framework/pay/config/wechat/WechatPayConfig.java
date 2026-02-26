package com.lanyang.framework.pay.config.wechat;

import com.lanyang.framework.pay.handler.WechatNotifyHandler;
import com.lanyang.framework.pay.service.WechatTransferService;
import com.lanyang.framework.pay.strategy.WechatPayStrategy;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RSAPublicKeyNotificationConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.refund.RefundService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lanyang
 * @date 2025/12/16
 * @des
 */
@Configuration
@EnableConfigurationProperties(WechatPayProperties.class)
@ConditionalOnProperty(prefix = "wechat.pay", value = "enabled", havingValue = "true")
@Import({WechatNotifyHandler.class, WechatPayStrategy.class, WechatTransferService.class})
public class WechatPayConfig {

    /**
     * 微信支付配置
     * 有多种构建Config模式，根据自己商户配置来选择
     * <a href="https://github.com/wechatpay-apiv3/wechatpay-java"/>
     * @param wechatPayProperties
     * @return
     */
    @Bean
    public Config config(WechatPayProperties wechatPayProperties){

        //自动更新微信支付平台证书，商户需要在微信支付公钥配置成使用平台证书模式
//        Config config =
//                new RSAAutoCertificateConfig.Builder()
//                        .merchantId(wechatPayProperties.getMchId())
//                        // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                        .privateKeyFromPath(wechatPayProperties.getPrivateKeyPath())
//                        .merchantSerialNumber(wechatPayProperties.getCertificateSerialNo())
//                        .apiV3Key(wechatPayProperties.getApiV3Key())
//                        .build();

        //使用本地平台公钥
        Config config =
                new RSAPublicKeyConfig.Builder()
                        .merchantId(wechatPayProperties.getMchId())
                        .privateKeyFromPath(wechatPayProperties.getPrivateKeyPath())
                        .publicKeyFromPath(wechatPayProperties.getPublicKeyPath())
                        .publicKeyId(wechatPayProperties.getWechatPayPublicKeyId())
                        .merchantSerialNumber(wechatPayProperties.getCertificateSerialNo())
                        .apiV3Key(wechatPayProperties.getApiV3Key())
                        .build();

        //使用本地的微信支付平台证书，不想使用 SDK 提供的定时更新平台证书
//        Config config =
//                new RSAConfig.Builder()
//                        .merchantId(merchantId)
//                        .privateKeyFromPath(privateKeyPath)
//                        .merchantSerialNumber(merchantSerialNumber)
//                        .wechatPayCertificatesFromPath(wechatPayCertificatePath)
//                        .build();

        return config;
    }

    @Bean
    public NotificationConfig notificationConfig(WechatPayProperties wechatPayProperties){
        NotificationConfig notificationConfig = new RSAPublicKeyNotificationConfig.Builder()
                .publicKeyFromPath(wechatPayProperties.getPublicKeyPath())
                .publicKeyId(wechatPayProperties.getWechatPayPublicKeyId())
                .apiV3Key(wechatPayProperties.getApiV3Key())
                .build();
        return notificationConfig;
    }

    @Bean
    public NotificationParser notificationParser(NotificationConfig notificationConfig){
        NotificationParser notificationParser = new NotificationParser(notificationConfig);
        return notificationParser;
    }

    @Bean
    public JsapiServiceExtension JsapiServiceExtension(Config config){
        JsapiServiceExtension jsapiService = new JsapiServiceExtension.Builder()
                .config(config)
                .build();
        return jsapiService;
    }

    @Bean
    public RefundService refundService(Config config){
        RefundService refundService = new RefundService.Builder().config(config).build();
        return refundService;
    }

}
