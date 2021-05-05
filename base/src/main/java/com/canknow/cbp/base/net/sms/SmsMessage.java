package com.canknow.cbp.base.net.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessage {
    private String to;

    private String templateCode;

    private String templateParams;

    private String freeSignName;
}
