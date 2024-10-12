package com.hejz.demo;

import com.hejz.pay.wx.service.RefundSuccessService;
import org.springframework.stereotype.Service;

@Service
public class RefundSuccessServiceImpl implements RefundSuccessService {
    @Override
    public void refundSuccess(String outTradeNo) {
        System.out.println("退款成功，订单号："+outTradeNo);
    }
}
