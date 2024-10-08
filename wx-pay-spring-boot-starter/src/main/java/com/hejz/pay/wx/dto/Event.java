package com.hejz.pay.wx.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Event {
    
    private String id;
    private String create_time;
    private String resource_type;
    private String event_type;
    private String summary;
    private Resource resource;

    @Data
    @NoArgsConstructor
    public static class Resource {
        private String original_type;
        private String algorithm;
        private String ciphertext;
        private String associated_data;
        private String nonce;
    }
}
