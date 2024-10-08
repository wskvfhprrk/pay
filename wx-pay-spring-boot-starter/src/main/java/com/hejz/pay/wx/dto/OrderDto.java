package com.hejz.pay.wx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String appid;
    private String mchid;
    private String description;
    private String out_trade_no;
    private String notify_url;
    private Amount amount;
}
