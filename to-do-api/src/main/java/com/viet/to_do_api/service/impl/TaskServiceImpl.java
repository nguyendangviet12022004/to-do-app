package com.viet.to_do_api.service.impl;

import java.util.List;

import com.viet.to_do_api.exception.task.ExistsException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.constant.StatusValue;
import com.viet.to_do_api.dto.task.TaskDto;
import com.viet.to_do_api.entity.task.Status;
import com.viet.to_do_api.entity.task.Task;
import com.viet.to_do_api.mapper.task.TaskMapper;
import com.viet.to_do_api.repository.TaskRepository;
import com.viet.to_do_api.service.TaskService;
import com.viet.to_do_api.specification.TaskSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskDto createTask(TaskDto taskDto) {

        // check exists
        if(checkExistsTaskByTitle(taskDto.getTitle())) {
            throw new ExistsException("Title is exists");
        }

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


    @Cacheable(value = "tasks", keyGenerator = "customKeyGenerator")
    @Override
    public List<TaskDto> getTasks(String title, List<StatusValue> status, List<Integer> tagIds,
            List<Integer> categoryIds, List<Integer> priorities) {

        // specification
        Specification<Task> spec = TaskSpecification.hasTitle(title)
                .and(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasTagId(tagIds))
                .and(TaskSpecification.hasCategoryId(categoryIds))
                .and(TaskSpecification.hasPriority(priorities));

        return taskRepository.findAll(spec).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public boolean checkExistsTaskByTitle(String title) {
        return this.taskRepository.existsByTitle(title);
    }

}
