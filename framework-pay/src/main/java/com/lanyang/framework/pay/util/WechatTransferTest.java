package com.lanyang.framework.pay.util;

import com.lanyang.framework.pay.domain.transfer.wechat.WechatTransferSceneReportInfo;
import com.lanyang.framework.pay.domain.transfer.wechat.WechatTransferToUserParam;
import com.lanyang.framework.pay.enums.wechat.WechatTransferSceneReportType;

/**
 * @author lanyang
 * @date 2025/12/12
 * @des
 */
public class WechatTransferTest {

    public static void main(String[] args) {
        WechatTransferToUserParam param = new WechatTransferToUserParam();
        param.setAppid("wxf9c12035468b9330");
        param.setOpenid("122");
        param.setTransfer_amount(10000L);
        param.setTransfer_scene_id("1000");
        param.setOut_bill_no("TR1234567890");
        param.setUser_name("张三");
        param.setTransfer_remark("转账测试");
        param.setNotify_url("https://lytest.leyuanint.com:9000/be/cloud/bsm/wechat/notify/wxPayBack");

        WechatTransferSceneReportInfo sceneReportInfo = new WechatTransferSceneReportInfo();
        sceneReportInfo.setInfo_type(WechatTransferSceneReportType.ACTIVITY.getCode());
        sceneReportInfo.setInfo_content("活动名称");
        param.getTransfer_scene_report_infos().add(sceneReportInfo);

        WechatTransferSceneReportInfo reward = new WechatTransferSceneReportInfo();
        reward.setInfo_type(WechatTransferSceneReportType.AWARD.getCode());
        reward.setInfo_content("新人任务奖励");
        param.getTransfer_scene_report_infos().add(reward);

//        TransferToUserResult transferToUserResult = WechatPayUtils.doTransfer(param);
//        System.out.println(transferToUserResult);
    }
}
