package com.viet.to_do_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viet.to_do_api.dto.auth.response.TokenResponse;
import com.viet.to_do_api.entity.Token;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    @Mapping(target = "code", source = "code")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "accountId", source = "account.id")
    TokenResponse toTokenResponse(Token token);
}
