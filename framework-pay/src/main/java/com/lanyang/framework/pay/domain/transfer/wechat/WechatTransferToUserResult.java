package com.lanyang.framework.pay.domain.transfer.wechat;

import com.lanyang.framework.pay.domain.credit.TransferToUserResult;
import com.lanyang.framework.pay.enums.wechat.WechatTransferStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des 微信转账结果
 */
@Data
@ApiModel("微信转账结果")
public class WechatTransferToUserResult extends TransferToUserResult {

    @ApiModelProperty("商户转账单号")
    public String outBillNo;

    @ApiModelProperty("微信支付单号")
    public String transferBillNo;

    @ApiModelProperty("转账时间")
    public String createTime;

    @ApiModelProperty("转账状态")
    public WechatTransferStatus state;

    @ApiModelProperty("跳转领取页面的package信息")
    public String packageInfo;
}
