# 封装一个扫描支付的starter【pay-spring-boot-starter】
1.WxNativePayTemplate ---> createOrder 方法
2.WxPayProperties---> 加载支付所有需要数据
3.WxPayAutoConfiguration --->创建WxNativePayTemplate的方法
4.META-INF/spring.factories【EnableAutoConfiguation=WxPayAutoConfiguration全类名】
或者 META-INF/spring/org.springframework.boot.autoconfigure.Autoconfiguration.imports 【WxPayAutoconfiguration全类名】

