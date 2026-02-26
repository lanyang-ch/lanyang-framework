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

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public interface PayStrategy {

    /**
     * 获取支付方式
     * 微信/支付宝
     * @return
     */
    PayType getPayType();

    /**
     * 预下单，创建微信/支付宝订单信息
     * 调起微信/支付宝支付
     * @param param
     * @return
     * @throws PayException
     */
    PreOrderResult createOrder(CreateOrderParam param) throws PayException;

    /**
     * 查询微信/支付宝订单信息
     * 支付宝支持按照支付宝订单号或者商户订单号查询
     * 微信目前只封装了按照商户订单号查询
     * @param param
     * @return
     * @throws PayException
     */
    QueryOrderResult queryOrder(QueryOrderParam param) throws PayException;

    /**
     * 关闭微信/支付宝订单
     * @param param
     * @throws PayException
     */
    void closeOrder(CloseOrderParam param) throws PayException;

    /**
     * 微信/支付宝订单退款
     * @param param
     * @throws PayException
     */
    void refundOrder(RefundOrderParam param) throws PayException;

    /**
     * 退款查询
     * @param param
     * @return
     * @throws PayException
     */
    RefundResult queryRefundOrder(QueryRefundOrderParam param) throws PayException;
}
