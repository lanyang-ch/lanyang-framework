package com.lanyang.framework.pay.domain;

import com.lanyang.framework.pay.enums.CommonTradeStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@Data
@ApiModel("响应结果基类")
public class BaseResult {

    @ApiModelProperty("响应结果码 SUCCESS-成功 FAIL-失败")
    protected CommonTradeStatus resultCode;

    @ApiModelProperty("错误编码")
    protected String errorCode;

    @ApiModelProperty("错误信息")
    protected String errorMsg;
}
