package com.canknow.cbp.base.net.email;

public interface IEmailSender {
    void send(String to, String subject, String body, boolean isBodyHtml);

    void send(String from, String to, String subject, String body, boolean isBodyHtml);
}
