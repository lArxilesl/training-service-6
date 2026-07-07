package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.exception.DependentEntityException;
import com.accenture.ems.emstraining.mapper.TrainingMapper;
import com.accenture.ems.emstraining.model.dto.TrainingDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.repository.TrainingDetailsRepository;
import com.accenture.ems.emstraining.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository repository;
    private final TrainingDetailsRepository detailsRepository;
//    private final TrainingTypeRepository typeRepository;
    private final TrainingMapper mapper;

    @Transactional(readOnly = true)
    public Optional<TrainingDTO> getById(Long id) {
        log.info("getById(): retrieving training by id={}", id);

        Optional<Training> training = repository.findById(id);

        if (!training.isPresent()) {
            log.info("getById(): not found training by id={}", id);
        } else {
            log.info("getById(): found training by id={}", id);
        }

        return training.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getAll() {
        log.info("getAll(): retrieving all trainings");

        List<Training> trainings = repository.findAll();

        if (trainings.isEmpty()) {
            log.info("getAll(): no trainings found");
        } else {
            log.info("getAll(): returning training list with size={}", trainings.size());
        }

        return mapper.toDTO(trainings);
    }

    @Transactional
    public TrainingDTO create(TrainingDTO trainingDTO) throws RuntimeException {
        log.info("create(): creating training");

//        Optional<TrainingType> typeOptional = typeRepository.findById(trainingDTO.getTrainingTypeId());
//        if (!typeOptional.isPresent()) {
//            log.info("create(): attempt to create training with an invalid type id={}",
//                    trainingDTO.getTrainingTypeId());
//            throw new DependentEntityException(String.format("Training type was not found with id %d",
//                    trainingDTO.getTrainingTypeId()));
//        }

//        TrainingType type = typeOptional.get();
        Training entity = mapper.toEntity(trainingDTO);

//        entity.setTrainingType(type);
        entity = repository.save(entity);

        log.info("create(): created training entity with id={}", entity.getId());
        return mapper.toDTO(entity);
    }

    @Transactional
    public Optional<TrainingDTO> updateById(Long id, TrainingDTO trainingDTO) throws RuntimeException {
        log.info("updateById(): updating training");

//        Optional<TrainingType> typeOptional = typeRepository.findById(trainingDTO.getTrainingTypeId());
//        if (!typeOptional.isPresent()) {
//            log.info("updateById(): attempt to create training with an invalid type id={}",
//                    trainingDTO.getTrainingTypeId());
//            throw new DependentEntityException(String.format("Training type was not found with id %d",
//                    trainingDTO.getTrainingTypeId()));
//        }

        Optional<Training> oldEntityOptional = repository.findById(id);
        if (!oldEntityOptional.isPresent()) {
            log.info("updateById(): attempt to update nonexistent training entity with id={}", id);
            return Optional.empty();
        }

//        TrainingType type = typeOptional.get();
        Training oldEntity = oldEntityOptional.get();

        mapper.updateEntityFromDTO(trainingDTO, oldEntity);

//        oldEntity.setTrainingType(type);
        oldEntity = repository.save(oldEntity);

        log.info("updateById(): updated training entity with id={}", oldEntity.getId());
        return Optional.of(mapper.toDTO(oldEntity));
    }

    @Transactional
    public boolean deleteById(Long id) throws RuntimeException {
        log.info("deleteById(): deleting training entity with id={}", id);
        Optional<Training> entityOptional = repository.findById(id);

        if (!entityOptional.isPresent()) {
            log.info("deleteById(): attempt to delete nonexistent training with id={}", id);
            return false;
        }

        Training entity = entityOptional.get();

        if (detailsRepository.existsByTrainingId(id)) {
            log.info("deleteById(): attempt to delete training with id={} which is reference by training details", id);
            throw new DependentEntityException("Training couldn't be deleted because it was referenced by some " +
                    "training details");
        }

        repository.delete(entity);

        log.info("deleteById(): deleted training entity with id={}", id);
        return true;
    }
}