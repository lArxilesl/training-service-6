package com.accenture.ems.emstraining.business.service;

import com.accenture.ems.emstraining.model.TrainingType;

import java.util.List;
import java.util.Optional;

public interface TrainingTypeService {
    Optional<TrainingType> getTrainingTypeById(Integer Id);
    List<TrainingType> getAll();
    void saveTrainingType(TrainingType TrainingType);
    TrainingType updateTrainingType(Integer Id, TrainingType TrainingType);
    void deleteTrainingType(Integer Id);
}
