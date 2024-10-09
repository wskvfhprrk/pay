package com.hejz.pay.wx.controller;

import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.pay.wx.dto.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class WxPayNotifyController {

    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;

    /**
     * 微信支付回调类
     * @param event
     * @return
     */
    @PostMapping("pay/wx/notify")
    public Map payNotify(@RequestBody Event event) {
        Map<String, String> result = wxNativePayTemplate.wxPayNotice(event);
        //如果含有订单号由为支付成功的订单，否则没有支付成功需要定时关闭订单
        if (result.get("out_trade_no") != null) {
//            log.info("订单号：" + result.get("out_trade_no") + "---支付成功");
            //根据规则状态为200，返回值为空值表示正确接收到微信支付信息，微信便不再重发信息，如果不为空值则还会重发
            return null;
        }
        return result;
    }
}
