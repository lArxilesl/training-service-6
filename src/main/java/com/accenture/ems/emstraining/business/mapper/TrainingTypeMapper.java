package com.accenture.ems.emstraining.business.mapper;

import com.accenture.ems.emstraining.business.repository.dao.TrainingTypeDAO;
import com.accenture.ems.emstraining.model.TrainingType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {
    TrainingType mapFromDAO(TrainingTypeDAO trainingTypeDAO);

    TrainingTypeDAO mapToDAO(TrainingType trainingType);

    List<TrainingType> mapListFromDAO(List<TrainingTypeDAO> TrainingTypeDAOList);

}
