package com.viet.to_do_api.service.impl;

import java.util.List;

import com.viet.to_do_api.config.security.AccountOidcUser;
import com.viet.to_do_api.config.security.AccountUserDetails;
import com.viet.to_do_api.exception.task.ExistsException;
import org.springframework.security.core.Authentication;
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

        // if title is exist
        if(checkCategoryExistsByTitle(categoryDto.getTitle())) {
            throw new ExistsException("Title already exists");
        }

        var category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(Authentication authentication) {


        return categoryRepository.findByAccountEmail(authentication.getName()).stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public boolean checkCategoryExistsByTitle(String title) {
        return this.categoryRepository.existsByTitle(title);
    }

}
