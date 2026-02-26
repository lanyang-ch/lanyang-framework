package com.lanyang.framework.pay.domain.jsapi.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lanyang.framework.pay.domain.jsapi.PreOrderResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/16
 * @des
 */
@Data
public class WechatPreOrderResult extends PreOrderResult{

    @ApiModelProperty("小程序id")
    private String appId;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("随机串")
    private String nonceStr;

    @ApiModelProperty("签名方式")
    private String signType;

    @ApiModelProperty("签名")
    private String paySign;

    @ApiModelProperty("预支付交易会话标识")
    @JsonProperty("package")
    private String packageVal;

}
