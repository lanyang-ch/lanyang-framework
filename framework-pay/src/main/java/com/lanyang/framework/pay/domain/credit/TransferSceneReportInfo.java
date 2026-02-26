package com.lanyang.framework.pay.domain.credit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des <a href = "https://pay.weixin.qq.com/doc/v3/merchant/4013774588"></a>
 */
@ApiModel("微信转账场景信息")
@Data
public class TransferSceneReportInfo {

    @ApiModelProperty("信息类型 需严格按照转账场景报备信息字段说明传参")
    private String info_type;

    @ApiModelProperty("信息内容 不能超过32个字符，商户所属转账场景下的信息内容，商户可按实际业务场景自定义传参")
    private String info_content;
}
