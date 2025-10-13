package com.viet.to_do_api.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
                @NotEmpty(message = "The email is required") @Email(message = "Email is not valid") String email,
                @NotEmpty(message = "The password is required") String password) {

}
