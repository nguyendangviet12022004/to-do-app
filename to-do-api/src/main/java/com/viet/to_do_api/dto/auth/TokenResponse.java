package com.viet.to_do_api.dto.auth;

import com.viet.to_do_api.constant.TokenCodeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String code;
    private TokenCodeType type;
    private Integer accountId;
}
