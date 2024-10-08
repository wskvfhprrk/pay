package com.hejz.pay.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "pay.wx")
@Component
public class WxPayProperties {
    /**
     * 密钥文件路径
     */
    private String privateKeyFilePath;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户证书序列号
     */
    private String mchSerialNo;
    /**
     * V3密钥
     */
    private String apiV3Key;
    /**
     * 绑定appID
     */
    private String appid;
    /**
     * 支付成功回调地址
     */
    private String notifyUrl;
}
