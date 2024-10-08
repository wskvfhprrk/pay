package com.hejz.pay.wx;

import com.alibaba.fastjson.JSON;
import com.hejz.pay.wx.dto.*;
import com.hejz.pay.wx.properties.WxPayProperties;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class WxNativePayTemplate {

    private WxPayProperties wxPayProperties;
    private CloseableHttpClient httpClient;

    public WxNativePayTemplate(WxPayProperties wxPayProperties, CloseableHttpClient httpClient) {
        this.wxPayProperties = wxPayProperties;
        this.httpClient = httpClient;
    }

    /**
     * 创建订单返回json格式数据
     *
     * @param money       订单金额（分）
     * @param description 订单描述
     * @param outTradeNo  订单号
     * @return
     */
    public String createOrder(Integer money, String outTradeNo, String description) {
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
        Amount amount = Amount.builder().total(money).build();
        OrderDto orderDto = OrderDto.builder()
                .amount(amount)
                .notify_url(wxPayProperties.getNotifyUrl())
                .appid(wxPayProperties.getAppid())
                .description(description)
                .mchid(wxPayProperties.getMchId())
                .out_trade_no(outTradeNo)
                .build();
        // 请求body参数
        String reqdata = JSON.toJSONString(orderDto);
        StringEntity entity = new StringEntity(reqdata, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        String result = "";

        //完成签名并执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                result = statusCode+"," + EntityUtils.toString(response.getEntity());
//                Map map = JSON.parseObject(EntityUtils.toString(response.getEntity()), Map.class);
//                System.out.println("url========>" + map.get("code_url"));
            } else if (statusCode == 204) { //处理成功，无返回Body
                result = statusCode+",success";
//                System.out.println("success");
            } else {
                result =  statusCode + "," + EntityUtils.toString(response.getEntity());
//                System.out.println("扫码支付创建订单失败：" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "500,扫码支付创建订单失败：" + e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据商户号和订单号查询订单
     *
     * @param outTradeNo 订单号
     * @return
     * @throws Exception
     */
    public String queryOrder(String outTradeNo) {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            //请求URL
            URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + outTradeNo);
            uriBuilder.setParameter("mchid", wxPayProperties.getMchId());

            //完成签名并执行请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader("Accept", "application/json");
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                result = statusCode+"," + EntityUtils.toString(response.getEntity());
            } else if (statusCode == 204) {
                result = "204,success";
            } else {
                result = statusCode + "," + EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "500,查询订单失败：" + e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 订单退款
     *
     * @param money      退款金额
     * @param total      订单总金额
     * @param outTradeNo 订单号
     * @return
     */
    public String refunds(Integer money, Integer total, String outTradeNo) {
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/refund/domestic/refunds");
        RefundsAmount amount = RefundsAmount.builder()
                .refund(money)
                .total(total)
                .currency("CNY")
                .build();
        RefundsDto refundsDto = RefundsDto.builder()
                .out_refund_no(outTradeNo)
                .out_trade_no(outTradeNo)
                .amount(amount)
                .build();
        // 请求body参数
        String reqdata = JSON.toJSONString(refundsDto);
        StringEntity entity = new StringEntity(reqdata, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        //完成签名并执行请求
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                result = "200," + EntityUtils.toString(response.getEntity());
            } else if (statusCode == 204) { //处理成功，无返回Body
                result = "204,success";
            } else {
                result = statusCode + "," + EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "500,退款失败：" + e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo 订单号
     * @return
     */
    public String closeOrder(String outTradeNo) {

        //请求URL
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + outTradeNo + "/close");
        //请求body参数
        String reqdata = "{\"mchid\": \"" + wxPayProperties.getMchId() + "\"}";

        StringEntity entity = new StringEntity(reqdata, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                result = "success,return body = " + EntityUtils.toString(response.getEntity());
            } else if (statusCode == 204) {
                result = "success";
            } else {
                result = "failed,resp code = " + statusCode + ",return body = " + EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "关闭失败：" + e.getMessage();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 收到付款信息解析数据
     * @param event
     * @return
     */
    public Map<String, String> wxPayNotice(Event event) {
//        log.info("----------------->微信支付通知");
        AesUtil aesUtil = new AesUtil(wxPayProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        Map<String, String> resultMap = new HashMap<>();
        try {
            String s = aesUtil.decryptToString(event.getResource().getAssociated_data().getBytes(StandardCharsets.UTF_8),
                    event.getResource().getNonce().getBytes(StandardCharsets.UTF_8),
                    event.getResource().getCiphertext());
            Map map = com.alibaba.fastjson2.JSON.parseObject(s, Map.class);
            resultMap.put("out_trade_no", map.get("out_trade_no").toString());
            if (map.get("trade_state").toString().equals("SUCCESS")) {
                System.out.println("收到订单：" + map.get("out_trade_no") + "的付款成功");
                resultMap.put("out_trade_no",map.get("out_trade_no").toString());
            } else {
                System.out.println("收到订单：" + map.get("out_trade_no") + "的付款未成功");
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.out.println("解析错误");
            resultMap.put("cade", "FAIL");
            resultMap.put("message", "失败");
        }
        return resultMap;
    }

}
