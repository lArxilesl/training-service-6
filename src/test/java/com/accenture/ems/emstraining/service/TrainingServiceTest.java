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
    private TrainingType mockTrainingType;
    private TrainingPostDTO mockTrainingPostDTO;
    private TrainingResponseDTO mockTrainingResponseDTO;

    @BeforeEach
    public void setUp() {
        mockTraining = Training
                .builder()
                .id(1L)
                .name("Test Course")
                .startDate(Instant.EPOCH)
                .endDate(Instant.MAX)
                .trainingType(TrainingType
                        .builder()
                        .id(1L)
                        .type("type1")
                        .build())
                .build();
        mockTrainingType = mockTraining.getTrainingType();
        mockTrainingResponseDTO = mapper.toResponseDTO(mockTraining);
        // didn't want to add mapstruct method just for a test
        mockTrainingPostDTO = TrainingPostDTO
                .builder()
                .name(mockTraining.getName())
                .startDate(mockTraining.getStartDate())
                .endDate(mockTraining.getEndDate())
                .trainingTypeId(mockTraining
                        .getTrainingType()
                        .getId())
                .build();
    }

    @Test
    public void testGetAllOk() {
        Training mockTrainingSecond = cloner.deepClone(mockTraining);
        mockTrainingSecond.setId(2L);
        mockTrainingSecond.setName("Test Course Second");

        List<TrainingResponseDTO> expected = Arrays.asList(mockTrainingResponseDTO,
                mapper.toResponseDTO(mockTrainingSecond));

        when(repository.findAllWithType()).thenReturn(Arrays.asList(mockTraining, mockTrainingSecond));

        List<TrainingResponseDTO> actual = service.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllEmpty() {
        when(repository.findAllWithType()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), service.getAll());
    }

    @Test
    public void testGetByIdOk() {
        when(repository.findByIdWithType(eq(mockTraining.getId()))).thenReturn(Optional.of(mockTraining));

        assertEquals(Optional.of(mockTrainingResponseDTO), service.getById(mockTraining.getId()));
    }

    @Test
    public void testGetByIdEmpty() {
        when(repository.findByIdWithType(anyLong())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), service.getById(1L));
    }

    @Test
    public void testCreateOk() {
        Training mockTrainingWithNullId = cloner.deepClone(mockTraining);
        mockTrainingWithNullId.setId(null);

        when(repository.save(eq(mockTrainingWithNullId))).thenReturn(mockTraining);
        when(typeRepository.findById(mockTrainingType.getId())).thenReturn(Optional.of(mockTrainingType));

        assertEquals(mockTrainingResponseDTO, service.create(mockTrainingPostDTO));
    }

    @Test
    public void testCreateBadType() {
        Long badTypeId = 999L;
        TrainingPostDTO mockTrainingPostDTOWithBadTypeId = cloner.deepClone(mockTrainingPostDTO);
        mockTrainingPostDTOWithBadTypeId.setTrainingTypeId(badTypeId);

        when(typeRepository.findById(eq(badTypeId))).thenReturn(Optional.empty());

        assertThrows(DependentEntityException.class, () -> service.create(mockTrainingPostDTOWithBadTypeId));
    }

    @Test
    public void testUpdateByIdOk() {
        TrainingPostDTO updatedTrainingPostDTO = cloner.deepClone(mockTrainingPostDTO);
        updatedTrainingPostDTO.setName("Test Course Updated");
        updatedTrainingPostDTO.setStartDate(Instant.EPOCH.plus(2, ChronoUnit.DAYS));
        Training updatedEntity = cloner.deepClone(mockTraining);
        updatedEntity.setName(updatedTrainingPostDTO.getName());
        updatedEntity.setStartDate(updatedTrainingPostDTO.getStartDate());
        TrainingResponseDTO updatedTrainingResponseDTO = mapper.toResponseDTO(updatedEntity);

        when(repository.findById(mockTraining.getId())).thenReturn(Optional.of(mockTraining));
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(typeRepository.findById(eq(mockTrainingType.getId()))).thenReturn(Optional.of(mockTrainingType));


        Optional<TrainingResponseDTO> actual = service.updateById(updatedEntity.getId(), updatedTrainingPostDTO);


        assertEquals(Optional.of(updatedTrainingResponseDTO), actual);
    }

    @Test
    public void testUpdateByIdEmpty() {
        Long emptyId = 1L;

        when(repository.findById(eq(emptyId))).thenReturn(Optional.empty());
        when(typeRepository.findById(eq(mockTrainingType.getId()))).thenReturn(Optional.of(mockTrainingType));


        Optional<TrainingResponseDTO> actual = service.updateById(emptyId, mockTrainingPostDTO);


        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testUpdateByIdBadType() {
        Long badTypeId = 999L;
        TrainingPostDTO mockTrainingPostDTOWithBadTypeId = cloner.deepClone(mockTrainingPostDTO);
        mockTrainingPostDTOWithBadTypeId.setTrainingTypeId(badTypeId);

        when(typeRepository.findById(eq(badTypeId))).thenReturn(Optional.empty());

        assertThrows(DependentEntityException.class, () -> service.updateById(mockTraining.getId(),
                mockTrainingPostDTOWithBadTypeId));
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
