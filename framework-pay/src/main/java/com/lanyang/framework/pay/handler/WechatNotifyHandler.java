package com.lanyang.framework.pay.handler;

import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lanyang
 * @date 2025/12/17
 * @des
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WechatNotifyHandler {

    private static final String NOTIFY_HEADER_SERIAL_NUMBER = "Wechatpay-Serial";
    private static final String NOTIFY_HEADER_TIMESTAMP = "Wechatpay-Timestamp";
    private static final String NOTIFY_HEADER_NONCE = "Wechatpay-Nonce";
    private static final String NOTIFY_HEADER_SIGNATURE = "Wechatpay-Signature";
    private static final String NOTIFY_HEADER_SIGN_TYPE = "Wechatpay-Sign-Type";

    private final NotificationParser notificationParser;

    /**
     * 解析微信支付回调
     * @param request
     * @param notificationJson
     * @return
     */
    public Transaction parsePayNotify(HttpServletRequest request, String notificationJson){
        RequestParam requestParam = buildRequestParam(request, notificationJson);
        Transaction wechatPayNotify = notificationParser.parse(requestParam, Transaction.class);
        return wechatPayNotify;
    }

    /**
     * 解析微信退款回调
     * @param request
     * @param notificationJson
     * @return
     */
    public RefundNotification parseRefundNotify(HttpServletRequest request, String notificationJson){
        RequestParam requestParam = buildRequestParam(request, notificationJson);
        RefundNotification wechatRefundNotify = notificationParser.parse(requestParam, RefundNotification.class);
        return wechatRefundNotify;
    }

    /**
     * 构建请求参数
     * @param request
     * @param notificationJson
     * @return
     */
    private RequestParam buildRequestParam(HttpServletRequest request, String notificationJson){

        String serialNumber = request.getHeader(NOTIFY_HEADER_SERIAL_NUMBER);
        String timestamp = request.getHeader(NOTIFY_HEADER_TIMESTAMP);
        String nonce = request.getHeader(NOTIFY_HEADER_NONCE);
        String signature = request.getHeader(NOTIFY_HEADER_SIGNATURE);
        // 非必需，默认WECHATPAY2-SHA256-RSA2048
        String signType = request.getHeader(NOTIFY_HEADER_SIGN_TYPE);

        // 2. 参数校验：空值拦截
        if (StringUtils.isAnyBlank(serialNumber, timestamp, nonce, signature)) {
            log.error("微信支付回调参数缺失 | serialNumber:{} | timestamp:{} | nonce:{} | signature:{}", serialNumber, timestamp, nonce, signature);
            throw new IllegalArgumentException("回调核心参数缺失，验签失败");
        }
        return new RequestParam.Builder()
                .serialNumber(serialNumber)
                .nonce(nonce)
                .signature(signature)
                .signType(signType)
                .timestamp(timestamp)
                .body(notificationJson)
                .build();
    }
}
