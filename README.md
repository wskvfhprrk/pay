# 微信支付starter
## 使用用starter
1.导入starter依赖
```xml
        <dependency>
            <groupId>com.hejz</groupId>
            <artifactId>wx-pay-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
2.yml中配置支付需要的数据（demo没有`application.yml`文件，需要自己配置）

支付配置：
```yaml
pay:
  #微信支付使用到的数据
  wx:
    #商户号
    mchId: 
    #v3加密序列商户证书序列号
    mchSerialNo: 
    #V3密钥
    apiV3Key: 
    #appID
    appid: 
    #支付回调域名（不需要写路径必须是https）
    notifyUrl: 
    #密钥文件路径——实际存放路径
    privateKeyFilePath: 
```
3.调用接口有

测试代码
```java
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
```
4.支付成功通知处理类只需要实现`PaySuccessService`类，写实现逻辑
```java
package com.hejz.pay.wx.service;

/**
 * 成功后处理的业务——只需要实现这个方法即可
 */
public interface PaySuccessService {
    /**
     * 订单成功后业务
     * @param outTradeNo
     */
    void success(String outTradeNo);
}
```
5.退款成功通知只需要实现`RefundSuccessService`类，写实现逻辑
```java
package com.hejz.pay.wx.service;


/**
 * 退款成功处理方法——需要实现这个类
 */
public interface RefundSuccessService {
    /**
     * 退款成功后
     * @param outTradeNo 订单号
     */
    void success(String outTradeNo);
}
```

