package com.viet.to_do_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MailService mailService;

    @GetMapping("/mail")
    public ResponseEntity<?> sendMail() {
        mailService.sendSimpleMail("viet@gmail.com", "Test Subject", "Test Body");
        return ResponseEntity.ok("Mail sent");
    }
}
