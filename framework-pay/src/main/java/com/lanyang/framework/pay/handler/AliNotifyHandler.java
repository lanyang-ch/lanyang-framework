package com.lanyang.framework.pay.handler;

import com.alipay.v3.ApiException;
import com.alipay.v3.util.AlipaySignature;
import com.lanyang.framework.pay.config.ali.AliPayInitProperties;
import com.lanyang.framework.pay.domain.jsapi.ali.AliPayNotify;
import com.lanyang.framework.pay.domain.jsapi.ali.AliRefundNotify;
import com.lanyang.framework.pay.exception.PayException;
import com.lanyang.framework.common.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lanyang
 * @date 2025/12/17
 * @des
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AliNotifyHandler {

    private final AliPayInitProperties aliPayInitProperties;

    /**
     * 解析支付回调
     * @param request
     * @return
     */
    public AliPayNotify parsePayNotify(HttpServletRequest request) {
        Map<String, String> notify = getNotifyMap(request);
        //公钥验签示例代码
        try {
            boolean  signVerified = AlipaySignature.verifyV1(notify, aliPayInitProperties.getPublicKey(), "UTF-8","RSA2") ;
            if (!signVerified){
                throw new PayException("验证签名失败");
            }
        } catch (ApiException e) {
            log.error( "验证签名失败{}", e);
            throw new PayException("验证签名失败");
        }

        AliPayNotify aliPayNotify = JsonUtils.formatJson2Bean(JsonUtils.toJsonString(notify), AliPayNotify.class);
        return aliPayNotify;
    }

    /**
     * 解析退款回调
     * @param request
     * @return
     */
    public AliRefundNotify parseRefundNotify(HttpServletRequest request) {
        Map<String, String> notify = getNotifyMap(request);
        try {
            boolean  signVerified = AlipaySignature.verifyV1(notify, aliPayInitProperties.getPublicKey(), "UTF-8","RSA2") ;
            if (!signVerified){
                throw new PayException("验证签名失败");
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new PayException("验证签名失败");

        }

        AliRefundNotify refundNotify = JsonUtils.formatJson2Bean(JsonUtils.toJsonString(notify), AliRefundNotify.class);
        return refundNotify;
    }

    /**
     * 获取回调参数
     * @param request
     * @return
     */
    private Map<String, String> getNotifyMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
