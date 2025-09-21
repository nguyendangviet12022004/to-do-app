package com.viet.to_do_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viet.to_do_api.dto.auth.RegisterRequest;
import com.viet.to_do_api.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "false")
    @Mapping(target = "authorities", ignore = true)
    Account toAccount(RegisterRequest request);
}
