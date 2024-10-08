package com.hejz.pay.wx.config;

import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.pay.wx.properties.WxPayProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.security.PrivateKey;

@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayAutoConfiguration {
    public CloseableHttpClient httpClient(WxPayProperties wxPayProperties) {
//        System.out.println("-------->创建了CloseableHttpClient对象");
        try {
            // 加载商户私钥（privateKey：私钥字符串）
            PrivateKey merchantPrivateKey = PemUtil
                    .loadPrivateKey(new FileInputStream((wxPayProperties.getPrivateKeyFilePath())));

            // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
            AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                    new WechatPay2Credentials(wxPayProperties.getMchId(), new PrivateKeySigner(wxPayProperties.getMchSerialNo(), merchantPrivateKey)), wxPayProperties.getApiV3Key().getBytes("utf-8"));

            // 初始化httpClient
            CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(wxPayProperties.getMchId(), wxPayProperties.getMchSerialNo(), merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier)).build();
            return httpClient;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建httpClient失败：" + e.getMessage());
        }
        return null;
    }

    @Bean
    public WxNativePayTemplate wxNativePayTemplate(WxPayProperties wxPayProperties) {
//        System.out.println("-------->创建了WxNativePayTemplate对象");
        return new WxNativePayTemplate(wxPayProperties, httpClient(wxPayProperties));
    }
}
