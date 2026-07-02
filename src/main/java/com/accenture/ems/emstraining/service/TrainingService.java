package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.exception.DependentEntityException;
import com.accenture.ems.emstraining.mapper.TrainingMapper;
import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.model.entity.TrainingType;
import com.accenture.ems.emstraining.repository.TrainingDetailsRepository;
import com.accenture.ems.emstraining.repository.TrainingRepository;
import com.accenture.ems.emstraining.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;
    private final TrainingDetailsRepository detailsRepository;
    private final TrainingTypeRepository typeRepository;
    private final TrainingMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = true)
    public Optional<TrainingResponseDTO> getById(Long id) {
        return repository
                .findByIdWithType(id)
                .map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getAll() {
        return mapper.toResponseDTO(repository.findAllWithType());
    }

    @Transactional
    public TrainingResponseDTO create(TrainingPostDTO postDTO) throws RuntimeException {
        Optional<TrainingType> typeOptional = typeRepository.findById(postDTO.getTrainingTypeId());
        if (!typeOptional.isPresent()) {
            throw new DependentEntityException(String.format("Training type was not found with id %d",
                    postDTO.getTrainingTypeId()));
        }

        TrainingType type = typeOptional.get();
        Training entity = mapper.toEntity(postDTO);

        entity.setTrainingType(type);
        entity = repository.save(entity);

        return mapper.toResponseDTO(entity);
    }

    @Transactional
    public Optional<TrainingResponseDTO> updateById(Long id, TrainingPostDTO postDTO) throws RuntimeException {
        Optional<TrainingType> typeOptional = typeRepository.findById(postDTO.getTrainingTypeId());
        if (!typeOptional.isPresent()) {
            throw new DependentEntityException(String.format("Training type was not found with id %d",
                    postDTO.getTrainingTypeId()));
        }

        Optional<Training> oldEntityOptional = repository.findById(id);
        if (!oldEntityOptional.isPresent()) {
            return Optional.empty();
        }

        TrainingType type = typeOptional.get();
        Training oldEntity = oldEntityOptional.get();

        mapper.updateEntityFromPostDTO(postDTO, oldEntity);

        oldEntity.setTrainingType(type);
        oldEntity = repository.save(oldEntity);

        return Optional.of(mapper.toResponseDTO(oldEntity));
    }

    @Transactional
    public boolean deleteById(Long id) throws RuntimeException {
        Optional<Training> entityOptional = repository.findById(id);

        if (!entityOptional.isPresent()) {
            return false;
        }

        Training entity = entityOptional.get();

        if (detailsRepository.existsByTrainingId(id)) {
            throw new DependentEntityException("Training couldn't be deleted because it was referenced by some " +
                    "training details");
        }

        repository.delete(entity);
        return true;
    }
}