package com.canknow.cbp.base.net.email;

import org.springframework.stereotype.Component;

@Component
public class NullEmailSender implements IEmailSender{
    @Override
    public void send(String to, String subject, String body, boolean isBodyHtml) {

    }

    @Override
    public void send(String from, String to, String subject, String body, boolean isBodyHtml) {

    }
}
