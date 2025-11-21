package com.viet.to_do_api.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.viet.to_do_api.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtFilter jwtFilter;

        private final AccountOidcUserService oidcUserService;
        private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

        private static final String[] AUTH_WHITELIST = {
                        "/pay-os/**",
                        "test/**",
                        "/auth/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs",
                        "/swagger-ui.html",
                        "/actuator/**"
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                // disable csrf for postman testing
                http.csrf(AbstractHttpConfigurer::disable);

                // filter request
                http
                                .authorizeHttpRequests(
                                                req -> req.requestMatchers(AUTH_WHITELIST).permitAll()
                                                                .anyRequest().authenticated())

                                // cors
                                .cors((cors) -> cors
                                                .configurationSource(apiConfigurationSource()))

                                // filter
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                                // session
                                .sessionManagement(ss -> ss
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // oauth2
                                .oauth2Login(
                                                login -> login
                                                                .userInfoEndpoint(e -> e
                                                                                .oidcUserService(oidcUserService))
                                                                .successHandler(oauth2LoginSuccessHandler))

                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                                .accessDeniedHandler(new CustomAccessDeniedHandler()));

                return http.build();
        }

        UrlBasedCorsConfigurationSource apiConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
