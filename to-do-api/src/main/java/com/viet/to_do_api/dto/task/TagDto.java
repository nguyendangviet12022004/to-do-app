package com.viet.to_do_api.dto.task;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Integer id;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotEmpty(message = "Color must not be empty")
    private String color;
}
