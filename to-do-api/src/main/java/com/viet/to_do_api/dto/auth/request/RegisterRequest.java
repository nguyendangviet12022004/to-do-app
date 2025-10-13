package com.viet.to_do_api.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Register request", description = "The request format to regsiter new account")
public class RegisterRequest {

    @Schema(description = "User email address")
    @Email(message = "The email is wrong format")
    private String email;

    @Schema(description = "Password of the account")
    @NotBlank(message = "Password is required")
    private String password;
}
