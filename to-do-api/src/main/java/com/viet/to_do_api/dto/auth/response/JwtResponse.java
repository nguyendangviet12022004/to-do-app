package com.viet.to_do_api.dto.auth.response;

public record JwtResponse(String accessToken, String refreshToken) {
    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
