package com.hejz.pay.wx.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundsDto {
    private RefundsAmount amount;
    private String out_trade_no;
    private String out_refund_no;
    private String notify_url;
}
