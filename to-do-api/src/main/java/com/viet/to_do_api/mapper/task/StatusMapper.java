package com.viet.to_do_api.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.viet.to_do_api.dto.task.StatusDto;
import com.viet.to_do_api.entity.task.Status;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatusMapper {
    StatusDto toDto(Status status);

    Status toEntity(StatusDto statusDto);
}
