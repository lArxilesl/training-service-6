package com.accenture.ems.emstraining.business.service.impl;

import com.accenture.ems.emstraining.business.mapper.TrainingTypeMapper;
import com.accenture.ems.emstraining.business.repository.TrainingTypeRepository;
import com.accenture.ems.emstraining.business.repository.dao.TrainingTypeDAO;
import com.accenture.ems.emstraining.business.service.TrainingTypeService;
import com.accenture.ems.emstraining.exceptions.TrainingTypeNotFoundException;
import com.accenture.ems.emstraining.exceptions.TrainingTypeServiceException;
import com.accenture.ems.emstraining.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository TrainingTypeRepository;

    public TrainingTypeServiceImpl(TrainingTypeRepository TrainingTypeRepository) {
        this.TrainingTypeRepository = TrainingTypeRepository;
    }

    @Override
    public Optional<TrainingType> getTrainingTypeById(Integer Id) {
        log.info("TrainingType id: {}",Id);
        return TrainingTypeRepository.findById(Id).map(TrainingTypeMapper::mapFromDAO);
    }

    @Override
    public List<TrainingType> getAll() {
        log.info("Getting all TrainingTypes: ");
        return TrainingTypeMapper.mapListFromDAO(TrainingTypeRepository.findAll());
    }

    @Override
    public void saveTrainingType(TrainingType TrainingType) {
        log.info("Saving TrainingType: {}", TrainingType.getType());
        try{
            TrainingTypeRepository.save(TrainingTypeMapper.mapToDAO(TrainingType));
        } catch (Exception e){
            log.error("Error saving TrainingType: {}", e.getMessage());
            throw new TrainingTypeServiceException("Error while saving TrainingType: ");
        }
    }

    @Override
    public TrainingType updateTrainingType(Integer Id, TrainingType TrainingType) {
        log.info("Updating TrainingType id: {}",Id);
        TrainingTypeRepository.findById(Id).orElseThrow(() -> new TrainingTypeNotFoundException("It is not found! "));
        TrainingTypeDAO TrainingTypeDAO = TrainingTypeMapper.mapToDAO(TrainingType);
        TrainingTypeDAO.setId(Id);
        return TrainingTypeMapper.mapFromDAO(TrainingTypeRepository.save(TrainingTypeDAO));
    }

    @Override
    public void deleteTrainingType(Integer Id) {
        log.info("Deleting TrainingType id: {}",Id);
        if(!TrainingTypeRepository.existsById(Id)){
            throw new TrainingTypeNotFoundException("It does not exist anymore! ");
        }
        TrainingTypeRepository.deleteById(Id);
    }
}
