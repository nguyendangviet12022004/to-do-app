package com.viet.to_do_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.dto.task.TagDto;
import com.viet.to_do_api.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Tag controller", description = "Controller for tag management functions (create, update, delete, etc.)")
@RestController
@RequestMapping("tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Create a new tag", description = "Create a new tag with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data") })
    @PostMapping
    public ResponseEntity<TagDto> createNewTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createNewTag(tagDto));
    }

}
