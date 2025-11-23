package com.viet.to_do_api.config.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @NotNull
    @Override
    public Object generate(@NotNull Object target, @NotNull Method method, @NotNull Object... params) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(params);
            return DigestUtils.md5Digest(bytes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
