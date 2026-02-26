package com.lanyang.framework.pay.strategy;

import com.lanyang.framework.pay.domain.jsapi.CloseOrderParam;
import com.lanyang.framework.pay.domain.jsapi.CreateOrderParam;
import com.lanyang.framework.pay.domain.jsapi.PreOrderResult;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderParam;
import com.lanyang.framework.pay.domain.jsapi.QueryOrderResult;
import com.lanyang.framework.pay.domain.jsapi.QueryRefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundOrderParam;
import com.lanyang.framework.pay.domain.jsapi.RefundResult;
import com.lanyang.framework.pay.enums.PayType;
import com.lanyang.framework.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lanyang
 * @date 2025/12/19
 * @des
 */
@Service
public class PayStrategyFactory {

    private Map<PayType, PayStrategy> payStrategyMap;

    @Autowired
    public PayStrategyFactory(List<PayStrategy> payStrategyList){

        payStrategyMap = payStrategyList.stream()
                .collect(Collectors.toMap(e -> e.getPayType(), Function.identity()));
    }

    /**
     * 预下单，创建微信/支付宝订单信息
     * 调起微信/支付宝支付
     * @param param
     * @return
     * @throws PayException
     */
    public PreOrderResult createOrder(CreateOrderParam param) throws PayException{
        return getPayStrategy(param.getPayType()).createOrder(param);
    }

    /**
     * 查询微信/支付宝订单信息
     * 支付宝支持按照支付宝订单号或者商户订单号查询
     * 微信目前只封装了按照商户订单号查询
     * @param param
     * @return
     * @throws PayException
     */
    public QueryOrderResult queryOrder(QueryOrderParam param) throws PayException{
        return getPayStrategy(param.getPayType()).queryOrder(param);

    }

    /**
     * 关闭微信/支付宝订单
     * @param param
     * @throws PayException
     */
    public void closeOrder(CloseOrderParam param) throws PayException{
        getPayStrategy(param.getPayType()).closeOrder(param);
    }

    /**
     * 微信/支付宝订单退款
     * @param param
     * @throws PayException
     */
    public void refundOrder(RefundOrderParam param) throws PayException{
        getPayStrategy(param.getPayType()).refundOrder(param);
    }

    /**
     * 退款查询
     * @param param
     * @return
     * @throws PayException
     */
    public RefundResult queryRefundOrder(QueryRefundOrderParam param) throws PayException{
        return getPayStrategy(param.getPayType()).queryRefundOrder(param);
    }


    /**
     * 获取支付方式
     * @param payType
     * @return
     */
    private PayStrategy getPayStrategy(PayType payType){

        if(payType == null){
            throw new PayException("不支持的支付方式");
        }

        PayStrategy payStrategy = payStrategyMap.get(payType);
        if(payStrategy == null){
            throw new PayException("不支持的支付方式");
        }
        return payStrategy;
    }

}
