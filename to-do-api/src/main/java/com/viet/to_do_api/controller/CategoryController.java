package com.viet.to_do_api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.viet.to_do_api.dto.task.CategoryDto;
import com.viet.to_do_api.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Category controller", description = "Controller for category management functions (create, update, delete, etc.)")
@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Add a new category", description = "Create a new category with the provided details.", parameters = {
            @Parameter(name = "categoryDto", description = "The details of the category to be created", required = true) })
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories")
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(Authentication authentication) {
        return ResponseEntity.ok(categoryService.getAllCategories(authentication));
    }

}
