package com.lanyang.framework.pay.domain.jsapi.ali;

import com.lanyang.framework.pay.domain.BaseParam;
import com.lanyang.framework.pay.domain.jsapi.QueryRefundOrderParam;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.enums.ali.RefundQueryOptions;
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
@ApiModel("支付宝查询退款订单参数")
@Getter
@Setter
public class AliQueryRefundOrderParam extends BaseParam implements QueryRefundOrderParam {


    @ApiModelProperty(value = "支付宝订单号")
    private String tradeNo;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "商户退款单号, 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的商户订单号")
    private String outRefundNo;

    @ApiModelProperty(value = "查询选项，商户通过上送该参数来定制同步需要额外返回的信息字段，数组格式。枚举支持： refund_detail_item_list：本次退款使用的资金渠道； gmt_refund_pay：退款执行成功的时间； deposit_back_info：银行卡冲退信息")
    private List<RefundQueryOptions> queryOptions;


    public AliQueryRefundOrderParam() {
        super(PayType.ALIPAY);
    }
}
