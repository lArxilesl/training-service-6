package com.accenture.ems.emstraining.mapper;

import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.model.dto.TrainingTypeResponseDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.model.entity.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingResponseDTO toResponseDTO(Training entity);

    TrainingTypeResponseDTO toResponseDTO(TrainingType entity);

    List<TrainingResponseDTO> toResponseDTO(List<Training> entities);

    Training toEntity(TrainingPostDTO postDTO);

    void updateEntityFromPostDTO(TrainingPostDTO postDTO, @MappingTarget Training entity);
}