package com.accenture.ems.emstraining.service.impl;

import com.accenture.ems.emstraining.exception.TrainingDetailsNotFoundException;
import com.accenture.ems.emstraining.repository.TrainingDetailsRepository;
import com.accenture.ems.emstraining.entity.TrainingDetailsEntity;
import com.accenture.ems.emstraining.mapper.TrainingDetailsMapper;
import com.accenture.ems.emstraining.model.TrainingDetailsDTO;
import com.accenture.ems.emstraining.service.TrainingDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingDetailsServiceImpl implements TrainingDetailsService {
    private final TrainingDetailsRepository trainingDetailsRepository;
    private final TrainingDetailsMapper trainingDetailsMapper;

    private TrainingDetailsEntity findTrainingDetails(Long trainingDetailsId) {
        return trainingDetailsRepository.findById(trainingDetailsId)
                .orElseThrow(TrainingDetailsNotFoundException::new);
    }

    @Override
    public Optional<TrainingDetailsDTO> getTrainingDetailsById(Long trainingDetailsId) {
        log.info("Received request to get training details with id: {}", trainingDetailsId);
        log.info("Training details with id {} found", trainingDetailsId);

        return trainingDetailsRepository.findById(trainingDetailsId)
                .map(trainingDetailsMapper::toDTO);
    }

    @Override
    public List<TrainingDetailsDTO> getAllTrainingDetails() {
        log.info("Received request to get all training details");
        List<TrainingDetailsEntity> trainingDetailsList = trainingDetailsRepository.findAll();
        log.info("Found {} training details", trainingDetailsList.size());

        return trainingDetailsMapper.toDTOList(trainingDetailsList);
    }

    @Override
    public TrainingDetailsDTO createTrainingDetails(TrainingDetailsDTO trainingDetailsDTO) {
        log.info("Received request to create training details");
        TrainingDetailsEntity newTrainingDetailsEntity = trainingDetailsMapper.toEntity(trainingDetailsDTO);
        TrainingDetailsEntity createdTrainingDetailsEntity = trainingDetailsRepository.save(newTrainingDetailsEntity);
        log.info("Training details created with id: {}", createdTrainingDetailsEntity.getId());

        return trainingDetailsMapper.toDTO(createdTrainingDetailsEntity);
    }

    @Override
    public TrainingDetailsDTO updateTrainingDetails(Long trainingDetailsId, TrainingDetailsDTO trainingDetailsDTO) {
        log.info("Received request to update training details with id: {}", trainingDetailsId);
        TrainingDetailsEntity trainingDetails = findTrainingDetails(trainingDetailsId);
        trainingDetailsMapper.updateEntityFromDTO(trainingDetailsDTO, trainingDetails);
        TrainingDetailsEntity updatedTrainingDetails = trainingDetailsRepository.save(trainingDetails);
        log.info("Training details with id {} updated", trainingDetailsId);

        return trainingDetailsMapper.toDTO(updatedTrainingDetails);
    }

    @Override
    public void deleteTrainingDetails(Long trainingDetailsId) {
        log.info("Received request to delete training details with id: {}", trainingDetailsId);
        TrainingDetailsEntity trainingDetails = findTrainingDetails(trainingDetailsId);
        trainingDetailsRepository.delete(trainingDetails);
        log.info("Training details with id {} deleted", trainingDetailsId);
    }
}