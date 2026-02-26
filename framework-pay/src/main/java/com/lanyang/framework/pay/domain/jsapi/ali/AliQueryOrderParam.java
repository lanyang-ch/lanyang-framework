package com.lanyang.framework.pay.domain.jsapi.ali;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.enums.ali.QueryOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/18
 * @des
 */
@ApiModel("支付宝查询订单参数")
@Getter
@Setter
public class AliQueryOrderParam extends BaseParam implements QueryOrderParam{

    @ApiModelProperty("商户订单号,和 tradeNo 二选一必填")
    private String outTradeNo;

    @ApiModelProperty("支付宝订单号，和 outTradeNo 二选一必填")
    private String tradeNo;

    @ApiModelProperty("查询选项")
    private List<QueryOptions> queryOptions;

    public AliQueryOrderParam() {
        super(PayType.ALIPAY);
    }

}
