package com.viet.to_do_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.dto.auth.request.ActivateAccountRequest;
import com.viet.to_do_api.dto.auth.request.LoginRequest;
import com.viet.to_do_api.dto.auth.request.RegisterRequest;
import com.viet.to_do_api.dto.auth.request.RefreshTokenRequest;
import com.viet.to_do_api.dto.exception.ExceptionResponse;
import com.viet.to_do_api.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication controller", description = "Controller for authentication function(login, regsiter, activate, etc)")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new account api", description = "Rest api to create new account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP status CREATED"),
            @ApiResponse(responseCode = "400", description = "Email is exsits or method parametter is invalid", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email is exsist or not", content = @Content(contentSchema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Email is not exsits or method parametter is invalid", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @Operation(summary = "Check if email exists")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkExistEmail(@RequestParam String email) {
        return ResponseEntity.ok(this.authService.checkExistEmail(email));
    }

    @Operation(summary = "Get activated code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Send code by email"),
            @ApiResponse(responseCode = "400", description = "Email is not exsits or method parametter is invalid", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/activate-account-code")
    public ResponseEntity<?> getActivateAccountCode(@RequestParam String email) throws MessagingException {
        authService.getActivateAccountCode(email);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate account by code")
    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@Valid @RequestBody ActivateAccountRequest request) {
        this.authService.activateAccount(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Login by email and password")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        var response = this.authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
