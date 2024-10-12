package com.hejz.demo;

import com.hejz.pay.wx.service.RefundSuccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RefundSuccessServiceImpl implements RefundSuccessService {
    @Override
    public void refundSuccess(String outTradeNo) {
        log.info("退款成功，订单号："+outTradeNo);
    }
}
