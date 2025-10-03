package com.viet.to_do_api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.viet.to_do_api.constant.TokenCodeType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String code;

    @Enumerated(EnumType.STRING)
    private TokenCodeType type;

    @ManyToOne
    private Account account;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private LocalDateTime validatedAt;

    private boolean isValidated;
}
