package com.viet.to_do_api.service;

import com.viet.to_do_api.dto.auth.request.ActivateAccountRequest;
import com.viet.to_do_api.dto.auth.request.LoginRequest;
import com.viet.to_do_api.dto.auth.request.RefreshTokenRequest;
import com.viet.to_do_api.dto.auth.request.RegisterRequest;
import com.viet.to_do_api.dto.auth.response.JwtResponse;

import jakarta.mail.MessagingException;

public interface AuthService {
    public void register(RegisterRequest request) throws Exception;

    public boolean checkExistEmail(String email);

    public void getActivateAccountCode(String email) throws MessagingException;

    public void activateAccount(ActivateAccountRequest request);

    public JwtResponse login(LoginRequest request);

    public JwtResponse refreshToken(RefreshTokenRequest request);
}
