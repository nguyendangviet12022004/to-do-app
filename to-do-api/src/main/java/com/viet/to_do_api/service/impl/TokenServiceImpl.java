package com.viet.to_do_api.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.viet.to_do_api.constant.TokenCodeType;
import com.viet.to_do_api.entity.Account;
import com.viet.to_do_api.entity.Token;
import com.viet.to_do_api.exception.auth.TokenExpiredException;
import com.viet.to_do_api.exception.auth.TokenNotExistsException;
import com.viet.to_do_api.repository.TokenRepository;
import com.viet.to_do_api.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    private String generateTokenCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateToken(int accountId, int expriedTimeInSecond, TokenCodeType type) {
        String code = generateTokenCode();

        // build token
        var token = Token.builder()
                .code(code)
                .type(type)
                .expiredAt(LocalDateTime.now().plusSeconds(expriedTimeInSecond))
                .account(
                        Account.builder()
                                .id(accountId)
                                .build())
                .build();

        var savedToken = tokenRepository.save(token);
        return savedToken.getCode();
    }

    @Override
    public boolean validateToken(String code) {
        Token token = tokenRepository.findByCode(code)
                .orElseThrow(() -> new TokenNotExistsException("Token is not exsits"));

        // if token is expired
        if (LocalDateTime.now().isAfter(token.getExpiredAt())) {
            throw new TokenExpiredException("Token is expired");
        }

        // update token
        token.setValidatedAt(LocalDateTime.now());
        token.setValidated(true);

        // save to db
        this.tokenRepository.save(token);

        return true;
    }

}
