package com.hejz.pay.wx.config;


import org.springframework.context.annotation.ComponentScan;

/**
 * 配置spring扫瞄类
 */
@ComponentScan(basePackages = {
        "com.hejz.pay.wx.controller",
        "com.hejz.pay.wx.service"
})
public class WebInit {
}
