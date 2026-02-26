package com.lanyang.framework.pay.service;

import com.lanyang.framework.pay.config.wechat.WechatPayProperties;
import com.lanyang.framework.pay.domain.credit.TransferToUserResult;
import com.lanyang.framework.pay.domain.transfer.wechat.WechatTransferToUserParam;
import com.lanyang.framework.pay.domain.transfer.wechat.WechatTransferToUserResult;
import com.lanyang.framework.pay.enums.CommonTradeStatus;
import com.lanyang.framework.pay.util.WXPayUtility;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author lanyang
 * @date 2026/1/4
 * @des
 */
@Service
@RequiredArgsConstructor
public class WechatTransferService {
    private final WechatPayProperties wechatPayProperties;
    private final OkHttpClient client;

    private static String HOST = "https://api.mch.weixin.qq.com";
    private static String METHOD = "POST";
    private static String PATH = "/v3/fund-app/mch-transfer/transfer-bills";

    public TransferToUserResult doTransfer(WechatTransferToUserParam request){

        if(request.getTransfer_amount() < 30L){
            //0.3元以下不允许输入姓名校验
            request.setUser_name(null);
        }else {
            //加密处理收款用户姓名
            request.setUser_name(WXPayUtility.encrypt(wechatPayProperties.getPublicKey(), request.getUser_name()));
        }
        String uri = PATH;
        String reqBody = WXPayUtility.toJson(request);

        Request.Builder reqBuilder = new Request.Builder().url(HOST + uri);
        reqBuilder.addHeader("Accept", "application/json");
        reqBuilder.addHeader("Wechatpay-Serial", wechatPayProperties.getWechatPayPublicKeyId());
        reqBuilder.addHeader("Authorization",
                WXPayUtility.buildAuthorization(wechatPayProperties.getMchId(),
                        wechatPayProperties.getCertificateSerialNo(),
                        wechatPayProperties.getPrivateKey(),
                        METHOD,
                        uri,
                        reqBody));
        reqBuilder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        reqBuilder.method(METHOD, requestBody);
        Request httpRequest = reqBuilder.build();

        // 发送HTTP请求
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            String respBody = WXPayUtility.extractBody(httpResponse);
            if (httpResponse.code() >= 200 && httpResponse.code() < 300) {
                // 2XX 成功，验证应答签名
                WXPayUtility.validateResponse(wechatPayProperties.getWechatPayPublicKeyId(), wechatPayProperties.getPublicKey(),
                        httpResponse.headers(), respBody);

                // 从HTTP应答报文构建返回数据
                WechatTransferToUserResult result = WXPayUtility.fromJson(respBody, WechatTransferToUserResult.class);
                result.setResultCode(CommonTradeStatus.SUCCESS);
                return result;
            } else {
                //失败
                WXPayUtility.ApiException apiException = new WXPayUtility.ApiException(httpResponse.code(), respBody, httpResponse.headers());
                TransferToUserResult result = new WechatTransferToUserResult();
                result.setResultCode(CommonTradeStatus.FAIL);
                result.setErrorMsg(apiException.getErrorMessage());
                result.setErrorCode(String.valueOf(apiException.getStatusCode()));
                return result;
            }
        } catch (IOException e) {
            TransferToUserResult result = new WechatTransferToUserResult();
            result.setResultCode(CommonTradeStatus.FAIL);
            result.setErrorMsg("网络异常");
            result.setErrorCode(e.getMessage());
            return result;
        }
    }
}
