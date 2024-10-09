package com.hejz.pay.wx.service;


/**
 * 退款成功处理方法——需要实现这个类
 */
public interface RefundSuccessService {
    /**
     * 退款成功后
     * @param outTradeNo 订单号
     */
    void success(String outTradeNo);
}
