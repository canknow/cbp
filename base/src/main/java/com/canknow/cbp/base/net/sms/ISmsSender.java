package com.canknow.cbp.base.net.sms;

public interface ISmsSender {
    void send(String to, String templateCode, String templateParams);

    void send(String to, String templateCode, String templateParams, String freeSignName);

    void send(SmsMessage sms);
}
