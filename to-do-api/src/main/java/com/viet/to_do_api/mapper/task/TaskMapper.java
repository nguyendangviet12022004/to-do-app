package com.viet.to_do_api.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.viet.to_do_api.dto.task.TaskDto;
import com.viet.to_do_api.entity.task.Task;
import com.viet.to_do_api.mapper.auth.AccountMapper;

@Mapper(componentModel = "spring", uses = {
        TagMapper.class, AttachmentMapper.class, CategoryMapper.class, CommentMapper.class,
        StatusMapper.class,
        AccountMapper.class }, nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);
}
