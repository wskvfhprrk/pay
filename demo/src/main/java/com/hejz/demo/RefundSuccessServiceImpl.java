package com.hejz.demo;

import com.hejz.pay.wx.service.RefundSuccessService;
import org.springframework.stereotype.Service;

@Service
public class RefundSuccessServiceImpl implements RefundSuccessService {
    @Override
    public void success(String outTradeNo) {
        System.out.println(outTradeNo);
    }
}
