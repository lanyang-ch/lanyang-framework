package com.lanyang.framework.pay.domain.transfer.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
public class WechatTransferToUserParam {

    @ApiModelProperty("商户AppID")
    public String appid;

    @ApiModelProperty("商户订单号")
    public String out_bill_no;

    @ApiModelProperty("转账场景ID")
    public String transfer_scene_id;

    @ApiModelProperty("用户openid")
    public String openid;

    @ApiModelProperty("用户姓名")
    public String user_name;

    @ApiModelProperty("转账金额")
    public Long transfer_amount;

    @ApiModelProperty("转账备注")
    public String transfer_remark;

    @ApiModelProperty("回调地址")
    public String notify_url;

    @ApiModelProperty("用户收款感知 用户收款时感知的收款原因。不填或填空，将展示转账场景的默认内容")
    public String user_recv_perception;

    @ApiModelProperty("转账场景报备信息")
    public List<WechatTransferSceneReportInfo> transfer_scene_report_infos = new ArrayList<>();
}
