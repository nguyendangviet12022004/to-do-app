package com.viet.to_do_api.service;

import java.util.Map;

public interface MailService {
    void sendSimpleMail(String to, String subject, String body);

    void sendHtmlMail(String to, String subject, String htmlTemplate, Map<String, Object> templateModel);
}
