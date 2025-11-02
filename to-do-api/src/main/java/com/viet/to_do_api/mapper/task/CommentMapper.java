package com.viet.to_do_api.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.viet.to_do_api.dto.task.CommentDto;
import com.viet.to_do_api.entity.task.Comment;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);
}
