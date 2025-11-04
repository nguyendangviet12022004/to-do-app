package com.viet.to_do_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viet.to_do_api.dto.task.CategoryDto;
import com.viet.to_do_api.mapper.task.CategoryMapper;
import com.viet.to_do_api.repository.CategoryRepository;
import com.viet.to_do_api.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        var category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

}
