package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.dto.TrainingPostDTO;
import com.accenture.ems.emstraining.model.dto.TrainingResponseDTO;
import com.accenture.ems.emstraining.model.dto.TrainingTypeResponseDTO;
import com.accenture.ems.emstraining.service.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingController.class)
public class TrainingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TrainingService service;

    private TrainingResponseDTO mockTrainingResponseDTO;
    private TrainingPostDTO mockTrainingPostDTO;

    @BeforeEach
    public void setUp() {
        mockTrainingResponseDTO = TrainingResponseDTO
                .builder()
                .id(1L)
                .name("Test Course")
                .startDate(Instant.EPOCH)
                .endDate(Instant.MAX)
                .trainingType(TrainingTypeResponseDTO
                        .builder()
                        .id(1L)
                        .type("type1")
                        .build())
                .build();
        mockTrainingPostDTO = TrainingPostDTO
                .builder()
                .name(mockTrainingResponseDTO.getName())
                .startDate(mockTrainingResponseDTO.getStartDate())
                .endDate(mockTrainingResponseDTO.getEndDate())
                .trainingTypeId(mockTrainingResponseDTO
                        .getTrainingType()
                        .getId())
                .build();
    }

    @Test
    public void testOkGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(mockTrainingResponseDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(mockTrainingResponseDTO.getId()))
                .andExpect(jsonPath("$.[0].name").value(mockTrainingResponseDTO.getName()))
                .andExpect(jsonPath("$.[0].startDate").value(mockTrainingResponseDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.[0].endDate").value(mockTrainingResponseDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.[0].trainingType.id").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getId()))
                .andExpect(jsonPath("$.[0].trainingType.type").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getType()));
    }

    @Test
    public void testEmptyGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testOkGetById() throws Exception {
        when(service.getById(mockTrainingResponseDTO.getId())).thenReturn(Optional.of(mockTrainingResponseDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTrainingResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingResponseDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingResponseDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingResponseDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingType.id").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getId()))
                .andExpect(jsonPath("$.trainingType.type").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getType()));
    }

    @Test
    public void testEmptyGetById() throws Exception {
        when(service.getById(anyLong())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testOkDeleteById() throws Exception {
        Long nonEmptyId = 10L;
        when(service.deleteById(eq(nonEmptyId))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/training/{id}", nonEmptyId);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void testEmptyDeleteById() throws Exception {
        Long emptyId = 10L;
        when(service.deleteById(eq(emptyId))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/training/{id}", emptyId);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testOkCreate() throws Exception {
        when(service.create(eq(mockTrainingPostDTO))).thenReturn(mockTrainingResponseDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/training")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingPostDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockTrainingResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingResponseDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingResponseDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingResponseDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingType.id").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getId()))
                .andExpect(jsonPath("$.trainingType.type").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getType()));
    }

    @Test
    public void testOkUpdateById() throws Exception {
        when(service.updateById(eq(mockTrainingResponseDTO.getId()), eq(mockTrainingPostDTO))).thenReturn(Optional.of(mockTrainingResponseDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/training/{id}", mockTrainingResponseDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingPostDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockTrainingResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingResponseDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingResponseDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingResponseDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingType.id").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getId()))
                .andExpect(jsonPath("$.trainingType.type").value(mockTrainingResponseDTO
                        .getTrainingType()
                        .getType()));
    }

    @Test
    public void testWrongTypeUpdateById() throws Exception {
        when(service.updateById(eq(mockTrainingResponseDTO.getId()), eq(mockTrainingPostDTO))).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/training/{id}",  mockTrainingResponseDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingPostDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
