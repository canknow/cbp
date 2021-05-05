package com.canknow.cbp.base.net.sms;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class SmsSenderBase implements ISmsSender {
    @Autowired
    protected ISmsSenderConfiguration iSmsSenderConfiguration;

    @Override
    public void send(String to, String templateCode, String templateParams) {
        sendSms(new SmsMessage(to, templateCode, templateParams, iSmsSenderConfiguration.getDefaultFreeSignName()));
    }

    @Override
    public void send(String to, String templateCode, String templateParams, String freeSignName) {
        sendSms(new SmsMessage(to, templateCode, templateParams, freeSignName));
    }

    @Override
    public void send(SmsMessage sms) {
        sendSms(sms);
    }

    protected abstract void sendSms(SmsMessage sms);
}
