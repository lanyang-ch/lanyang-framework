package com.lanyang.framework.pay.config.ali;

import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.api.AlipayTradeFastpayRefundApi;
import com.alipay.v3.util.model.AlipayConfig;
import com.lanyang.framework.pay.exception.PayException;
import com.lanyang.framework.pay.handler.AliNotifyHandler;
import com.lanyang.framework.pay.strategy.AliPayStrategy;
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
@EnableConfigurationProperties({AliPayInitProperties.class, AliPayCreditDepositProperties.class})
@ConditionalOnProperty(prefix = "alipay.pay", value = "enabled", havingValue = "true")
@Import({AliPayStrategy.class, AliNotifyHandler.class})
public class AliPayInitConfig {

    @Bean
    public AlipayConfig alipayConfig(AliPayInitProperties aliPayInitProperties) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(aliPayInitProperties.getServerUrl());
        alipayConfig.setAppId(aliPayInitProperties.getAppId());
        alipayConfig.setPrivateKey(aliPayInitProperties.getPrivateKey());
        alipayConfig.setAlipayPublicKey(aliPayInitProperties.getPublicKey());
        return alipayConfig;
    }

    @Bean
    public ApiClient apiClient(AlipayConfig alipayConfig){
        ApiClient DEFAULT_CLIENT = com.alipay.v3.Configuration.getDefaultApiClient();
        try {
            DEFAULT_CLIENT.setAlipayConfig(alipayConfig);
        } catch (ApiException e) {
            throw new PayException("支付宝工具类初始化失败，请检查配置");
        }
        return DEFAULT_CLIENT;
    }

    @Bean
    public AlipayTradeApi alipayTradeApi(ApiClient apiClient){
        return new AlipayTradeApi(apiClient);
    }

    @Bean
    public AlipayTradeFastpayRefundApi alipayTradeFastpayRefundApi (ApiClient apiClient){
        return new AlipayTradeFastpayRefundApi (apiClient);
    }

}
