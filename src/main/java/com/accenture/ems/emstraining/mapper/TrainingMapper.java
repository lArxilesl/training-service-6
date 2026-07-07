package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.model.dto.TrainingDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingDTO toDTO(Training entity);
    List<TrainingDTO> toDTO(List<Training> entities);

    @Mapping(target = "id", ignore = true)
    Training toEntity(TrainingDTO trainingDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TrainingDTO trainingDTO, @MappingTarget Training entity);
}