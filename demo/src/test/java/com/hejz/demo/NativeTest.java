package com.hejz.demo;

import com.hejz.pay.wx.WxNativePayTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class NativeTest {

    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;
    private String orderId="12345678931";
    @Test
    public void createOrder(){
        String s = wxNativePayTemplate.createOrder(1, orderId, "订单18");
        log.info(s);
    }
    @Test
    public void query(){
        String s = wxNativePayTemplate.queryOrder(orderId);
        log.info(s);
    }

    @Test
    public void refunds(){
        String refunds = wxNativePayTemplate.refunds(1, 1, orderId);
        log.info(refunds);
    }
    @Test
    public void closeOrder(){
        String s = wxNativePayTemplate.closeOrder(orderId);
        log.info(s);
    }
}
