package com.canknow.cbp.email;

import com.canknow.cbp.base.net.email.EmailSettingNames;
import com.canknow.cbp.base.net.email.IEmailSender;
import com.canknow.cbp.base.settings.ISettingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Primary
public class EmailSender implements IEmailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ISettingManager settingManager;

    @Override
    public void send(String to, String subject, String body, boolean isBodyHtml) {
        if (isBodyHtml) {
            sendMimeMail(settingManager.getSettingValue(EmailSettingNames.DefaultFromAddress), to, subject, body);
        }
        else {
            sendText(settingManager.getSettingValue(EmailSettingNames.DefaultFromAddress), to, subject, body);
        }
    }

    private void sendMimeMail(String from, String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(mimeMessage);
        }
        catch (Exception ignored) {

        }
    }

    private void sendText(String from, String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void send(String from, String to, String subject, String body, boolean isBodyHtml) {
        if (isBodyHtml) {
            sendMimeMail(from, to, subject, body);
        }
        else {
            sendText(from, to, subject, body);
        }
    }
}
