package com.hejz.demo;

import com.hejz.pay.wx.service.PaySuccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付回调
 */
@Service
@Slf4j
public class PaySuccessServiceImpl implements PaySuccessService {
    @Override
    public void paySuccess(String outTradeNo) {
        log.info("付款成功，订单号：" + outTradeNo);
    }
}
