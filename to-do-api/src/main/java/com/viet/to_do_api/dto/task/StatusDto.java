package com.viet.to_do_api.dto.task;

import java.time.LocalDateTime;

import com.viet.to_do_api.constant.StatusValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {

    private Integer id;

    private StatusValue value;

    private LocalDateTime timestamp;
}
