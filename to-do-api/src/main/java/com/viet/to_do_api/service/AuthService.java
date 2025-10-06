package com.viet.to_do_api.service;

import com.viet.to_do_api.dto.auth.ActivateAccountRequest;
import com.viet.to_do_api.dto.auth.RegisterRequest;

import jakarta.mail.MessagingException;

public interface AuthService {
    public void register(RegisterRequest request) throws Exception;

    public boolean checkExistEmail(String email);

    public void getActivateAccountCode(String email) throws MessagingException;

    public void activateAccount(ActivateAccountRequest request);
}
