package com.viet.to_do_api.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.viet.to_do_api.dto.task.TagDto;
import com.viet.to_do_api.entity.task.Tag;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    Tag toEntity(TagDto tagDto);

    TagDto toDto(Tag tag);
}
