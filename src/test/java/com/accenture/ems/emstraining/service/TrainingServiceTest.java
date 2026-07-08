package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.exception.DependentEntityException;
import com.accenture.ems.emstraining.mapper.TrainingMapper;
import com.accenture.ems.emstraining.model.dto.TrainingDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.repository.TrainingRepository;
import com.rits.cloning.Cloner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    private final Cloner cloner = new Cloner();
    @Mock
    private TrainingRepository repository;
    @InjectMocks
    private TrainingService service;
    @Spy
    private TrainingMapper mapper = Mappers.getMapper(TrainingMapper.class);
    private Training mockTraining;
    private Training mockTrainingWithoutId;
    private TrainingDTO mockTrainingDTO;
    private TrainingDTO mockTrainingDTOWithoutId;

    @BeforeEach
    public void setUp() {
        mockTraining = Training
                .builder()
                .id(1L)
                .name("Test Course")
                .startDate(Instant.EPOCH)
                .endDate(Instant.MAX)
                .trainingTypeId(1L)
                .build();
        mockTrainingDTO = mapper.toDTO(mockTraining);

        mockTrainingWithoutId = cloner.deepClone(mockTraining);
        mockTrainingWithoutId.setId(null);

        mockTrainingDTOWithoutId = cloner.deepClone(mockTrainingDTO);
        mockTrainingDTOWithoutId.setId(null);
    }

    @Test
    public void testGetAllOk() {
        Training mockTrainingSecond = cloner.deepClone(mockTraining);
        mockTrainingSecond.setId(2L);
        mockTrainingSecond.setName("Test Course Second");
        TrainingDTO mockSecondTrainingDTO = mapper.toDTO(mockTrainingSecond);

        List<TrainingDTO> expected = Arrays.asList(mockTrainingDTO, mockSecondTrainingDTO);

        when(repository.findAll()).thenReturn(Arrays.asList(mockTraining, mockTrainingSecond));

        List<TrainingDTO> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<TrainingDTO> actual = service.getAll();

        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void testGetByIdOk() {
        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));

        Optional<TrainingDTO> actual = service.getById(mockTraining.getId());

        assertEquals(Optional.of(mockTrainingDTO), actual);
    }

    @Test
    public void testGetByIdEmpty() {
        Long emptyId = 1L;

        when(repository.findById(emptyId)).thenReturn(Optional.empty());

        Optional<TrainingDTO> actual = service.getById(emptyId);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testCreateOk() {
        when(repository.saveAndFlush(mockTrainingWithoutId)).thenReturn(mockTraining);

        TrainingDTO actual = service.create(mockTrainingDTOWithoutId);

        assertEquals(mockTrainingDTO, actual);
    }

    @Test
    public void testCreateBadType() {
        doThrow(DataIntegrityViolationException.class)
                .when(repository)
                .saveAndFlush(mockTrainingWithoutId);

        assertThrows(DependentEntityException.class, () -> service.create(mockTrainingDTOWithoutId));
    }

    @Test
    public void testUpdateByIdOk() {
        Training updatedEntity = cloner.deepClone(mockTraining);
        updatedEntity.setName("Test Course Updated");
        updatedEntity.setStartDate(Instant.EPOCH.plus(2, ChronoUnit.DAYS));
        TrainingDTO updatedTrainingDTO = mapper.toDTO(updatedEntity);

        // old entity check
        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));
        when(repository.saveAndFlush(updatedEntity)).thenReturn(updatedEntity);

        Optional<TrainingDTO> actual = service.updateById(updatedEntity.getId(), updatedTrainingDTO);

        assertEquals(Optional.of(updatedTrainingDTO), actual);
    }

    @Test
    public void testUpdateByIdEmpty() {
        Long emptyId = 1L;

        when(repository.findById(emptyId)).thenReturn(Optional.empty());

        Optional<TrainingDTO> actual = service.updateById(emptyId, mockTrainingDTO);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testUpdateByIdBadType() {
        Long badTypeId = 999L;
        TrainingDTO mockTrainingDTOWithBadTypeId = cloner.deepClone(mockTrainingDTO);
        mockTrainingDTOWithBadTypeId.setTrainingTypeId(badTypeId);
        Training mockTrainingWithBadTypeId = mapper.toEntity(mockTrainingDTOWithBadTypeId);
        mockTrainingWithBadTypeId.setId(1L);

        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));
        doThrow(DataIntegrityViolationException.class)
                .when(repository)
                .saveAndFlush(mockTrainingWithBadTypeId);

        assertThrows(DependentEntityException.class, () -> service.updateById(mockTraining.getId(),
                mockTrainingDTOWithBadTypeId));
    }

    @Test
    public void testDeleteByIdOk() {
        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));

        assertTrue(service.deleteById(mockTraining.getId()));
    }

    @Test
    public void testDeleteByIdEmpty() {
        Long emptyId = 999L;
        when(repository.findById(emptyId)).thenReturn(Optional.empty());

        assertFalse(service.deleteById(emptyId));
    }

    @Test
    public void testDeleteByIdDependent() {
        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));
        doThrow(DataIntegrityViolationException.class)
                .when(repository)
                .delete(mockTraining);

        assertThrows(DependentEntityException.class, () -> service.deleteById(mockTraining.getId()));
    }
}
