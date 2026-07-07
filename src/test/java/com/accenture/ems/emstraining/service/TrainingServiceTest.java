package com.accenture.ems.emstraining.service;

import com.accenture.ems.emstraining.exception.DependentEntityException;
import com.accenture.ems.emstraining.mapper.TrainingMapper;
import com.accenture.ems.emstraining.model.dto.TrainingDTO;
import com.accenture.ems.emstraining.model.entity.Training;
import com.accenture.ems.emstraining.repository.TrainingDetailsRepository;
import com.accenture.ems.emstraining.repository.TrainingRepository;
import com.accenture.ems.emstraining.repository.TrainingTypeRepository;
import com.rits.cloning.Cloner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    private final Cloner cloner = new Cloner();
    @Mock
    private TrainingRepository repository;
    @Mock
    private TrainingDetailsRepository detailsRepository;
    @Mock
    private TrainingTypeRepository typeRepository;
    @InjectMocks
    private TrainingService service;
    @Spy
    private TrainingMapper mapper = Mappers.getMapper(TrainingMapper.class);
    private Training mockTraining;
    private TrainingDTO mockTrainingDTO;

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
        assertEquals(Collections.emptyList(), service.getAll());
    }

    @Test
    public void testGetByIdOk() {
        when(repository.findById(eq(mockTraining.getId()))).thenReturn(Optional.of(mockTraining));

        assertEquals(Optional.of(mockTrainingDTO), service.getById(mockTraining.getId()));
    }

    @Test
    public void testGetByIdEmpty() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), service.getById(1L));
    }

    @Test
    public void testCreateOk() {
        Training mockTrainingWithNullId = cloner.deepClone(mockTraining);
        mockTrainingWithNullId.setId(null);

        when(repository.save(eq(mockTrainingWithNullId))).thenReturn(mockTraining);
//        when(typeRepository.findById(mockTrainingType.getId())).thenReturn(Optional.of(mockTrainingType));

        assertEquals(mockTrainingDTO, service.create(mockTrainingDTO));
    }

    @Test
    public void testCreateBadType() {
        Long badTypeId = 999L;
        TrainingDTO mockTrainingDTOWithBadTypeId = cloner.deepClone(mockTrainingDTO);
        mockTrainingDTOWithBadTypeId.setTrainingTypeId(badTypeId);

        when(typeRepository.findById(eq(badTypeId))).thenReturn(Optional.empty());

        assertThrows(DependentEntityException.class, () -> service.create(mockTrainingDTOWithBadTypeId));
    }

    @Test
    public void testUpdateByIdOk() {
        Training updatedEntity = cloner.deepClone(mockTraining);
        updatedEntity.setName("Test Course Updated");
        updatedEntity.setStartDate(Instant.EPOCH.plus(2, ChronoUnit.DAYS));
        TrainingDTO updatedTrainingDTO = mapper.toDTO(updatedEntity);

        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
//        when(typeRepository.findById(eq(mockTrainingType.getId()))).thenReturn(Optional.of(mockTrainingType));


        Optional<TrainingDTO> actual = service.updateById(updatedEntity.getId(), updatedTrainingDTO);


        assertEquals(Optional.of(updatedTrainingDTO), actual);
    }

    @Test
    public void testUpdateByIdEmpty() {
        Long emptyId = 1L;

        when(repository.findById(eq(emptyId))).thenReturn(Optional.empty());
//        when(typeRepository.findById(eq(mockTrainingType.getId()))).thenReturn(Optional.of(mockTrainingType));


        Optional<TrainingDTO> actual = service.updateById(emptyId, mockTrainingDTO);


        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testUpdateByIdBadType() {
        Long badTypeId = 999L;
        TrainingDTO mockTrainingDTOWithBadTypeId = cloner.deepClone(mockTrainingDTO);
        mockTrainingDTOWithBadTypeId.setTrainingTypeId(badTypeId);

        when(typeRepository.findById(eq(badTypeId))).thenReturn(Optional.empty());

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
        when(detailsRepository.existsByTrainingId(mockTraining.getId())).thenReturn(true);
        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));

        assertThrows(DependentEntityException.class, () -> service.deleteById(mockTraining.getId()));
    }
}
