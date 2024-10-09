package com.hejz.pay.wx.service;

/**
 * 成功后处理的业务——只需要实现这个方法即可
 */
public interface PaySuccessService {
    /**
     * 订单成功后业务
     * @param outTradeNo
     */
    void success(String outTradeNo);
}
