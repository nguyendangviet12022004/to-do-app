package com.viet.to_do_api.dto.task;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data transfer object for Task")
public class TaskDto {
    private Integer id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private CategoryDto category;

    private List<TagDto> tags;

    private StatusDto currentStatus;

    private List<StatusDto> statusHistory;

    private List<CommentDto> comments;

    private List<AttachmentDto> attachments;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must be at most 5")
    private Integer priority;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime dueDate;

}
