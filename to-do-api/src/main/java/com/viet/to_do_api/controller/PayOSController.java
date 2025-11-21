package com.viet.to_do_api.controller;

import com.viet.to_do_api.entity.payment.Donate;
import com.viet.to_do_api.repository.DonateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("pay-os")
@RequiredArgsConstructor
public class PayOSController {

    private final DonateRepository donateRepository;

    @Value("${payos.client-id}")
    private String payosClientId;

    @Value("${payos.api-key}")
    private String payosApiKey;

    @Value("${payos.checksum-key}")
    private String checkSumKey;

    @Value("${payos.return-url}")
    private String returnUrl;

    @Value("${payos.cancel-url}")
    private String cancelUrl;

    @GetMapping("/create-payment-link")
    public ResponseEntity<CreatePaymentLinkResponse> createPaymentLink(@RequestParam(required = false, defaultValue = "2000") long amount, @RequestParam(required = false, defaultValue = "Donate") String description) {


        PayOS payOS = new PayOS(payosClientId, payosApiKey, checkSumKey);

        CreatePaymentLinkRequest request = CreatePaymentLinkRequest.builder()
                .amount(amount)
                .description(description)
                .orderCode(System.currentTimeMillis() / 1000)
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .build();
        return ResponseEntity.ok(payOS.paymentRequests().create(request));
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Object webhook) {
        PayOS payOS = new PayOS(payosClientId, payosApiKey, checkSumKey);

        var data = payOS.webhooks().verify(webhook);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime transactionDateTime = LocalDateTime.parse(data.getTransactionDateTime(), formatter);

        Donate donate = Donate.builder()
                .code(data.getCode())
                .description(data.getDescription())
                .orderCode(data.getOrderCode())
                .amount(data.getAmount())
                .transactionDateTime(transactionDateTime)
                .build();

        donateRepository.save(donate);

        return ResponseEntity.ok("Webhook received");
    }
}
