package com.viet.to_do_api.service;

import java.util.List;

import com.viet.to_do_api.constant.StatusValue;
import com.viet.to_do_api.dto.task.TaskDto;

public interface TaskService {
     TaskDto createTask(TaskDto taskDto);

     List<TaskDto> getTasks(String title, List<StatusValue> status, List<Integer> tagIds,
            List<Integer> categoryIds,
            List<Integer> priorities);

     boolean checkExistsTaskByTitle(String title);
}
