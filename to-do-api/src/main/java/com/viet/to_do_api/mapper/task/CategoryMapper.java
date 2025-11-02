package com.viet.to_do_api.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.viet.to_do_api.dto.task.CategoryDto;
import com.viet.to_do_api.entity.task.Category;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
}
