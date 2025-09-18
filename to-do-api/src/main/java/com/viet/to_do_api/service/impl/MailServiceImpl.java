package com.viet.to_do_api.service.impl;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.service.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String body) {

        var message = new SimpleMailMessage();

        // set attributes
        message.setFrom("noreply@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        // send mail
        emailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String htmlTemplate, Map<String, Object> templateModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendHtmlMail'");
    }

}
