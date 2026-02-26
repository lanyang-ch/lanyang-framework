package com.lanyang.framework.pay.domain.jsapi.ali;

import com.alipay.v3.model.TradeFundBill;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("支付宝查询订单结果")
@Data
public class AliQueryOrderResult extends QueryOrderResult{

    @ApiModelProperty("买家支付宝用户唯一标识,针对未使用openId的商户")
    private String buyerUserId;

    @ApiModelProperty("支付渠道")
    private List<TradeFundBill> fundBillList;

}
