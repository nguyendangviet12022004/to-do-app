package com.viet.to_do_api.service;

import java.util.List;

import com.viet.to_do_api.dto.task.CategoryDto;
import org.springframework.security.core.Authentication;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories(Authentication authentication);

    boolean checkCategoryExistsByTitle(String title);

}
