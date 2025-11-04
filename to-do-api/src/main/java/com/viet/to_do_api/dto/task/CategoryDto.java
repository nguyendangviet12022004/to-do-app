package com.viet.to_do_api.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for Category")
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "Title is required")
    private String title;
}
