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
    #支付回调路径，必须是https
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
4.支付回调处理,只需要调用`wxNativePayTemplate.wxPayNotice(event)`处理即可，Event为已经封装好的微信支付实体类，注意微信[微信支付应答规则](https://pay.weixin.qq.com/docs/merchant/apis/native-payment/payment-notice.html):
>通知应答
>
>接收成功： HTTP应答状态码需返回200或204，无需返回应答报文。
>
>接收失败： HTTP应答状态码需返回5XX或4XX，同时需返回应答报文，

**也就是应答报文为空即可，否则微信支付作为没有通知到重试通知**

示例代码
```java
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
```

