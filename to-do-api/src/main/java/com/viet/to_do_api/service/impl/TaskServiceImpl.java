package com.viet.to_do_api.service.impl;

import org.springframework.stereotype.Service;

import com.viet.to_do_api.constant.StatusValue;
import com.viet.to_do_api.dto.task.TaskDto;
import com.viet.to_do_api.entity.task.Status;
import com.viet.to_do_api.mapper.task.TaskMapper;
import com.viet.to_do_api.repository.TaskRepository;
import com.viet.to_do_api.service.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        var task = taskMapper.toEntity(taskDto);

        // initialize default status
        task.setCurrentStatus(
                Status.builder()
                        .value(StatusValue.TO_DO)
                        .task(task)
                        .build());

        // save task
        var savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

}
