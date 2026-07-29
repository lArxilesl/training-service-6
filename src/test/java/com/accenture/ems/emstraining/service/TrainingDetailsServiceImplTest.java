package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.entity.TrainingDetailsEntity;
import com.accenture.ems.emstraining.exception.TrainingDetailsNotFoundException;
import com.accenture.ems.emstraining.mapper.TrainingDetailsMapper;
import com.accenture.ems.emstraining.model.TrainingDetailsDTO;
import com.accenture.ems.emstraining.repository.TrainingDetailsRepository;
import com.accenture.ems.emstraining.service.impl.TrainingDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TrainingDetailsServiceImplTest {
    @Mock
    private TrainingDetailsRepository trainingDetailsRepository;

    @Mock
    private TrainingDetailsMapper trainingDetailsMapper;

    @InjectMocks
    private TrainingDetailsServiceImpl trainingDetailsService;

    private TrainingDetailsEntity trainingDetailsEntity;
    private TrainingDetailsDTO trainingDetailsDTO;

    @BeforeEach
    void setUpObject() {

        trainingDetailsDTO = TrainingDetailsDTO.builder()
                .id(1L)
                .trainingId(1L)
                .employeeId(1L)
                .mentorRelationId(1L)
                .finalPresentationDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .build();

        trainingDetailsEntity = new TrainingDetailsEntity();
        trainingDetailsEntity.setId(trainingDetailsDTO.getId());
        trainingDetailsEntity.setTrainingId(trainingDetailsDTO.getTrainingId());
        trainingDetailsEntity.setEmployeeId(trainingDetailsDTO.getEmployeeId());
        trainingDetailsEntity.setMentorRelationId(trainingDetailsDTO.getMentorRelationId());
        trainingDetailsEntity.setFinalPresentationDate(trainingDetailsDTO.getFinalPresentationDate());
        trainingDetailsEntity.setStartDate(trainingDetailsDTO.getStartDate());
        trainingDetailsEntity.setEndDate(trainingDetailsDTO.getEndDate());
    }


    @Test
    void shouldReturnTrainingDetailsWhenTrainingDetailsExists() {
        when(trainingDetailsRepository.findById(1L)).thenReturn(Optional.of(trainingDetailsEntity));
        when(trainingDetailsMapper.toDTO(trainingDetailsEntity)).thenReturn(trainingDetailsDTO);
        Optional<TrainingDetailsDTO> result = trainingDetailsService.getTrainingDetailsById(1L);

        assertEquals(Optional.of(trainingDetailsDTO), result);
        verify(trainingDetailsRepository).findById(1L);
        verify(trainingDetailsMapper).toDTO(trainingDetailsEntity);
    }

    @Test
    void shouldThrowTrainingDetailsNotFoundExceptionWhenTrainingDetailsDoesNotExist() {
        when(trainingDetailsRepository.findById(10000L)).thenReturn(Optional.empty());

        Optional<TrainingDetailsDTO> result = trainingDetailsService.getTrainingDetailsById(10000L);
        assertEquals(Optional.empty(), result);

        verify(trainingDetailsRepository).findById(10000L);
        verifyNoInteractions(trainingDetailsMapper);
    }

    @Test
    void shouldReturnAllTrainingDetailsWhenTrainingDetailsExist() {
        when(trainingDetailsRepository.findAll()).thenReturn(Arrays.asList(trainingDetailsEntity));
        when(trainingDetailsMapper.toDTOList(Arrays.asList(trainingDetailsEntity))).thenReturn(Arrays.asList(trainingDetailsDTO));

        assertEquals(
                Arrays.asList(trainingDetailsDTO),
                trainingDetailsService.getAllTrainingDetails());
        verify(trainingDetailsRepository).findAll();
        verify(trainingDetailsMapper)
                .toDTOList(Arrays.asList(trainingDetailsEntity));
    }

    @Test
    void shouldCreateTrainingDetails() {
        when(trainingDetailsMapper.toEntity(trainingDetailsDTO)).thenReturn(trainingDetailsEntity);
        when(trainingDetailsRepository.save(trainingDetailsEntity)).thenReturn(trainingDetailsEntity);
        when(trainingDetailsMapper.toDTO(trainingDetailsEntity)).thenReturn(trainingDetailsDTO);
        TrainingDetailsDTO created = trainingDetailsService.createTrainingDetails(trainingDetailsDTO);

        assertEquals(trainingDetailsDTO, created);
        verify(trainingDetailsMapper).toEntity(trainingDetailsDTO);
        verify(trainingDetailsRepository).save(trainingDetailsEntity);
        verify(trainingDetailsMapper).toDTO(trainingDetailsEntity);
    }

    @Test
    void shouldUpdateTrainingDetailsWhenTrainingDetailsExists() {
        when(trainingDetailsRepository.findById(1L)).thenReturn(Optional.of(trainingDetailsEntity));
        doNothing().when(trainingDetailsMapper).updateEntityFromDTO(trainingDetailsDTO, trainingDetailsEntity);
        when(trainingDetailsRepository.save(trainingDetailsEntity)).thenReturn(trainingDetailsEntity);
        when(trainingDetailsMapper.toDTO(trainingDetailsEntity)).thenReturn(trainingDetailsDTO);
        TrainingDetailsDTO updated = trainingDetailsService.updateTrainingDetails(1L, trainingDetailsDTO);

        assertEquals(trainingDetailsDTO, updated);
        verify(trainingDetailsRepository).findById(1L);
        verify(trainingDetailsMapper) .updateEntityFromDTO(trainingDetailsDTO, trainingDetailsEntity);
        verify(trainingDetailsRepository).save(trainingDetailsEntity);
        verify(trainingDetailsMapper).toDTO(trainingDetailsEntity);
    }

    @Test
    void shouldThrowTrainingDetailsNotFoundExceptionWhenUpdatingNonExistentTrainingDetails() {
        when(trainingDetailsRepository.findById(10000L)).thenReturn(Optional.empty());

        assertThrows(
                TrainingDetailsNotFoundException.class,
                () -> trainingDetailsService.updateTrainingDetails(10000L, trainingDetailsDTO));
        verify(trainingDetailsRepository).findById(10000L);
        verifyNoInteractions(trainingDetailsMapper);
    }

    @Test
    void shouldDeleteTrainingDetailsWhenTrainingDetailsExists() {
        when(trainingDetailsRepository.findById(1L)).thenReturn(Optional.of(trainingDetailsEntity));
        trainingDetailsService.deleteTrainingDetails(1L);

        verify(trainingDetailsRepository).findById(1L);
        verify(trainingDetailsRepository).delete(trainingDetailsEntity);
        verifyNoInteractions(trainingDetailsMapper);
    }

    @Test
    void shouldThrowTrainingDetailsNotFoundExceptionWhenDeletingNonExistentTrainingDetails() {
        when(trainingDetailsRepository.findById(10000L)).thenReturn(Optional.empty());

        assertThrows(
                TrainingDetailsNotFoundException.class,
                () -> trainingDetailsService.deleteTrainingDetails(10000L));
        verify(trainingDetailsRepository).findById(10000L);
        verifyNoInteractions(trainingDetailsMapper);
    }
}
