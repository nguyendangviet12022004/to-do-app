package com.viet.to_do_api.service;

import com.viet.to_do_api.constant.TokenCodeType;
import com.viet.to_do_api.dto.auth.response.TokenResponse;

public interface TokenService {
    public String generateToken(int accountId, int expriedTimeInSecond, TokenCodeType type);

    public boolean validateToken(String code);

    public TokenResponse getTokenByCode(String code);
}
