package com.viet.to_do_api.dto.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.viet.to_do_api.constant.ExceptionCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Respoonse for error", description = "Response schema when get an error")
public class ExceptionResponse {
    @Schema(description = "Error message")
    private String message;

    @Schema(description = "HTTP status of the error")
    private HttpStatus status;

    @Schema(description = "API path where the error occurred")
    private String path;

    @Schema(description = "The timestamp when the error occurred")
    private LocalDateTime timestamp;

    @Schema(description = "Custom exception code")
    private ExceptionCode code;
}
