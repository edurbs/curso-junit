package com.algaworks.junit.blog.business;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EmailSendingManager {

    void sendEmail(Message message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("algatestes.algaworks", ""));
            email.setSSLOnConnect(true);
            email.setFrom("algatestes@gmail.com");
            email.setSubject(message.getSubject());
            email.setMsg(message.getContent());
            email.addTo(message.getSubject());
            email.send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
