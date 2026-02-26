package com.lanyang.framework.pay.domain.jsapi;

import com.lanyang.framework.pay.enums.PayType;
import io.swagger.annotations.ApiModel;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
@ApiModel("创建订单参数")
public interface CreateOrderParam{

    PayType getPayType();

}
