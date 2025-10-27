package com.viet.to_do_api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.service.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MailService mailService;

    @GetMapping("/mail")
    public ResponseEntity<?> sendMail() throws MessagingException {
        mailService.sendHtmlMail("viet.ngdang.dev@gmail.com", "Test html", "mail/activate-code", Map.of("code", 54987));
        return ResponseEntity.ok("Mail sent");
    }

    @GetMapping()
    public String Hello() {
        return "";
    }

}
