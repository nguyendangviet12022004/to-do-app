package com.viet.to_do_api.service;

import com.viet.to_do_api.constant.TokenCodeType;

public interface TokenService {
    public String generateToken(int accountId, int expriedTimeInSecond, TokenCodeType type);
}
