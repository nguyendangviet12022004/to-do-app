package com.viet.to_do_api.service.impl;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.viet.to_do_api.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

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
    public void sendHtmlMail(String to, String subject, String htmlTemplate, Map<String, Object> templateModel)
            throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        // direction
        helper.setFrom("noreply@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // context
        Context context = new Context();
        context.setVariables(templateModel);

        // html content
        String htmlContent = this.templateEngine.process(htmlTemplate, context);

        helper.setText(htmlContent, true);

        this.emailSender.send(message);
    }

}
