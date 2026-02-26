package com.lanyang.framework.pay.domain.jsapi;

import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("关闭订单参数")
public interface CloseOrderParam {

    PayType getPayType();
}
