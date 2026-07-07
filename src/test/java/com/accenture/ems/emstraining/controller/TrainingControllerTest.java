package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.model.dto.TrainingDTO;
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

    private TrainingDTO mockTrainingDTO;

    @BeforeEach
    public void setUp() {
        mockTrainingDTO = TrainingDTO
                .builder()
                .id(1L)
                .name("Test Course")
                .startDate(Instant.EPOCH)
                .endDate(Instant.MAX)
                .trainingTypeId(1L)
                .build();
    }

    @Test
    public void testOkGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(mockTrainingDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(mockTrainingDTO.getId()))
                .andExpect(jsonPath("$.[0].name").value(mockTrainingDTO.getName()))
                .andExpect(jsonPath("$.[0].startDate").value(mockTrainingDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.[0].endDate").value(mockTrainingDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.[0].trainingTypeId").value(mockTrainingDTO.getTrainingTypeId()));
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
        when(service.getById(mockTrainingDTO.getId())).thenReturn(Optional.of(mockTrainingDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/training/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTrainingDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingTypeId").value(mockTrainingDTO.getTrainingTypeId()));
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
        when(service.deleteById(nonEmptyId)).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/training/{id}", nonEmptyId);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void testEmptyDeleteById() throws Exception {
        Long emptyId = 10L;
        when(service.deleteById(emptyId)).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/training/{id}", emptyId);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testOkCreate() throws Exception {
        when(service.create(mockTrainingDTO)).thenReturn(mockTrainingDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/training")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockTrainingDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingTypeId").value(mockTrainingDTO.getTrainingTypeId()));
    }

    @Test
    public void testOkUpdateById() throws Exception {
        when(service.updateById(mockTrainingDTO.getId(), mockTrainingDTO)).thenReturn(Optional.of(mockTrainingDTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/training/{id}", mockTrainingDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockTrainingDTO.getId()))
                .andExpect(jsonPath("$.name").value(mockTrainingDTO.getName()))
                .andExpect(jsonPath("$.startDate").value(mockTrainingDTO
                        .getStartDate()
                        .toString()))
                .andExpect(jsonPath("$.endDate").value(mockTrainingDTO
                        .getEndDate()
                        .toString()))
                .andExpect(jsonPath("$.trainingTypeId").value(mockTrainingDTO.getTrainingTypeId()));
    }

    @Test
    public void testWrongTypeUpdateById() throws Exception {
        when(service.updateById(mockTrainingDTO.getId(), mockTrainingDTO)).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/training/{id}", mockTrainingDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockTrainingDTO))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
