package com.hejz.demo;

import com.hejz.pay.wx.service.PaySuccessService;
import org.springframework.stereotype.Service;

/**
 * 支付回调
 */
@Service
public class PaySuccessServiceImpl implements PaySuccessService {
    @Override
    public void success(String outTradeNo) {
        System.out.println(outTradeNo);
    }
}
