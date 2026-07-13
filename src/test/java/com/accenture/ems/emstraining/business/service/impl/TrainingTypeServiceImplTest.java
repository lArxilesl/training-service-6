package com.accenture.ems.emstraining.business.service.impl;
/*
import com.accenture.ems.emstraining.business.repository.TrainingTypeRepository;
import com.accenture.ems.emstraining.business.repository.dao.TrainingTypeDAO;
import com.accenture.ems.emstraining.exceptions.TrainingTypeNotFoundException;
import com.accenture.ems.emstraining.model.TrainingType;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;*/

public class TrainingTypeServiceImplTest {
   /* @Mock
    TrainingTypeRepository TrainingTypeRepository;
    @InjectMocks
    TrainingTypeServiceImpl TrainingTypeService;

    @Test
    public void getAll_returnsList(){
        TrainingTypeDAO dao = new TrainingTypeDAO();
        dao.setId(1);
        dao.setType("Type");
        when(TrainingTypeRepository.findAll()).thenReturn(Collections.singletonList(dao));
        List<TrainingType> result = TrainingTypeService.getAll();
        assertNotNull(result);
        assertEquals(1,result.size());
    }
    @Test
    public void getTrainingType_found_returnsOptional(){
        TrainingTypeDAO dao = new TrainingTypeDAO();
        dao.setId(1);
        when(TrainingTypeRepository.findById(1)).thenReturn(Optional.of(dao));
        Optional<TrainingType> result = TrainingTypeService.getTrainingTypeById(1);
        assertTrue(result.isPresent());
    }
    @Test
    public void getTrainingType_notFound_returnsEmpty(){
        when(TrainingTypeRepository.findById(1)).thenReturn(Optional.empty());
        Optional<TrainingType> result = TrainingTypeService.getTrainingTypeById(1);
        assertFalse(result.isPresent());
    }
    @Test
    public void saveTrainingType_callsSave(){
        TrainingType TrainingType = new TrainingType();
        TrainingType.setType("Test");
        TrainingTypeService.saveTrainingType(TrainingType);
        verify(TrainingTypeRepository,times(1)).save(any());
    }
    @Test
    public void deleteTrainingType_exists_callsDelete(){
        when(TrainingTypeRepository.existsById(1)).thenReturn(true);
        TrainingTypeService.deleteTrainingType(1);
        verify(TrainingTypeRepository,times(1)).deleteById(1);
    }
    @Test
    public void deleteTrainingType_notFound_throwsException(){
        when(TrainingTypeRepository.existsById(1)).thenReturn(false);
        assertThrows(TrainingTypeNotFoundException.class,()-> TrainingTypeService.deleteTrainingType(1));
    }*/
}
