package com.hejz.demo;

import com.hejz.pay.wx.WxNativePayTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NativeTest {

    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;
    private String orderId="12345678921";
    @Test
    public void createOrder(){
        String s = wxNativePayTemplate.createOrder(1, orderId, "订单18");
        System.out.println(s);
    }
    @Test
    public void query(){
        String s = wxNativePayTemplate.queryOrder(orderId);
        System.out.println(s);
    }

    @Test
    public void refunds(){
        String refunds = wxNativePayTemplate.refunds(1, 1, orderId);
        System.out.println(refunds);
    }
    @Test
    public void closeOrder(){
        String s = wxNativePayTemplate.closeOrder(orderId);
        System.out.println(s);
    }
}
