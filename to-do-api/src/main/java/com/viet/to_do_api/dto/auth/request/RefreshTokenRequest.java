package com.viet.to_do_api.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank(message = "refresh token must not be blank") String refreshToken) {
}
