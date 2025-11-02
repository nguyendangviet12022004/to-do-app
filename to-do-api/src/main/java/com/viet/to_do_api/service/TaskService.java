package com.viet.to_do_api.service;

import com.viet.to_do_api.dto.task.TaskDto;

public interface TaskService {
    public TaskDto createTask(TaskDto taskDto);
}
