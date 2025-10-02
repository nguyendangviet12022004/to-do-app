package com.viet.to_do_api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(name = "Activate account request", description = "Contains actiavate code that sent to registered email")
public record ActivateAccountRequest(
        @Schema(name = "Activate code") @NotEmpty(message = "The activate code is require") String code) {
}