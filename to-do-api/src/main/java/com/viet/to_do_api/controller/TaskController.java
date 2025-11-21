package com.viet.to_do_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.constant.StatusValue;
import com.viet.to_do_api.dto.task.TaskDto;
import com.viet.to_do_api.service.TaskService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
@Tag(name = "Task controller", description = "Controller for task management functions (create, update, delete, etc.)")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        taskDto = taskService.createTask(taskDto);
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam(required = false) String title,
            @RequestParam(required = false) List<StatusValue> status,
            @RequestParam(required = false) List<Integer> tagIds,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) List<Integer> priorities) {
        return ResponseEntity.ok(taskService.getTasks(title, status, tagIds, categoryIds, priorities));
    }

    @GetMapping("check-exists-title")
    public ResponseEntity<Boolean> checkExistsTitle(@RequestParam String title) {
        return ResponseEntity.ok(taskService.checkExistsTaskByTitle(title));
    }
}
