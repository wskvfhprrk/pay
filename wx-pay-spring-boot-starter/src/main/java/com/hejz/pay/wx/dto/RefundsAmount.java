package com.hejz.pay.wx.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundsAmount {
    private Integer total;
    private Integer refund;
    private String currency;
}
