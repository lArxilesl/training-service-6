package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.entity.TrainingDetailsEntity;
import com.accenture.ems.emstraining.model.TrainingDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingDetailsMapper {
    TrainingDetailsDTO toDTO(TrainingDetailsEntity entity);

    @Mapping(target = "id", ignore = true)
    TrainingDetailsEntity toEntity(TrainingDetailsDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TrainingDetailsDTO dto, @MappingTarget TrainingDetailsEntity entity);

    List<TrainingDetailsDTO> toDTOList(List<TrainingDetailsEntity> entities);
}
