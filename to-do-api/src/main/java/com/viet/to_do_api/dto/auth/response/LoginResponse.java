package com.viet.to_do_api.dto.auth.response;

public record LoginResponse(String accessToken, String refreshToken) {
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
