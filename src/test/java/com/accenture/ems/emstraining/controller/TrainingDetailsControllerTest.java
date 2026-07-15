package com.accenture.ems.emstraining.controller;

import com.accenture.ems.emstraining.exception.TrainingDetailsNotFoundException;
import com.accenture.ems.emstraining.model.TrainingDetailsDTO;
import com.accenture.ems.emstraining.service.TrainingDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingDetailsController.class)
public class TrainingDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainingDetailsService trainingDetailsService;

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
    }

    @Test
    void shouldReturnTrainingDetailsWhenTrainingDetailsExists() throws Exception {
        when(trainingDetailsService.getTrainingDetailsById(1L)).thenReturn(Optional.of(trainingDetailsDTO));
        mockMvc.perform(get("/training-details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.trainingId").value(1));

        verify(trainingDetailsService).getTrainingDetailsById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenTrainingDetailsDoesNotExist() throws Exception {
        when(trainingDetailsService.getTrainingDetailsById(1L)).thenThrow(new TrainingDetailsNotFoundException());

        mockMvc.perform(get("/training-details/1")).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllTrainingDetailsWhenTrainingDetailsExist() throws Exception {
        when(trainingDetailsService.getAllTrainingDetails()).thenReturn(Arrays.asList(trainingDetailsDTO));

        mockMvc.perform(get("/training-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].trainingId").value(1));
    }

    @Test
    void shouldCreateAndReturnTrainingDetails() throws Exception {
        when(trainingDetailsService.createTrainingDetails(any(TrainingDetailsDTO.class))).thenReturn(trainingDetailsDTO);

        mockMvc.perform(post("/training-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainingDetailsDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateTrainingDetailsWhenTrainingDetailsExists() throws Exception {
        when(trainingDetailsService.updateTrainingDetails(eq(1L), any(TrainingDetailsDTO.class))).thenReturn(trainingDetailsDTO);

        mockMvc.perform(put("/training-details/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainingDetailsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentTrainingDetails() throws Exception {
        when(trainingDetailsService.updateTrainingDetails(eq(1L), any(TrainingDetailsDTO.class))).thenThrow(new TrainingDetailsNotFoundException());

        mockMvc.perform(put("/training-details/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainingDetailsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTrainingDetailsWhenTrainingDetailsExists() throws Exception {
        mockMvc.perform(delete("/training-details/1")).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentTrainingDetails() throws Exception {
        doThrow(new TrainingDetailsNotFoundException())
                .when(trainingDetailsService)
                .deleteTrainingDetails(1L);

        mockMvc.perform(delete("/training-details/1")).andExpect(status().isNotFound());
    }
}
