package com.hejz.demo;

import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.pay.wx.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付回调
 */
@RestController
public class PayNotifyController {

    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;

    @PostMapping("pay/wx/payNotify")
    public Map payNotify(@RequestBody Event event){
        Map<String, String> result = wxNativePayTemplate.wxPayNotice(event);
        //如果含有订单号由为支付成功的订单，否则没有支付成功需要定时关闭订单
        if(result.get("out_trade_no")!=null){
            System.out.println("订单号："+result.get("out_trade_no")+"---支付成功");
            //根据规则状态为200，返回值为空值表示正确接收到微信支付信息，微信便不再重发信息，如果不为空值则还会重发
            return null;
        }
        return result;
    }
}
