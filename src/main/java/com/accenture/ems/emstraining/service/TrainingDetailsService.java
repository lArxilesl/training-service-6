package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.model.TrainingDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface TrainingDetailsService {
    Optional<TrainingDetailsDTO> getTrainingDetailsById(Long id);
    List<TrainingDetailsDTO> getAllTrainingDetails();
    TrainingDetailsDTO createTrainingDetails(TrainingDetailsDTO dto);
    TrainingDetailsDTO updateTrainingDetails(Long id, TrainingDetailsDTO dto);
    void deleteTrainingDetails(Long id);
}
