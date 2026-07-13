package com.accenture.ems.emstraining.web.controller;
/*
import com.accenture.ems.emstraining.business.service.TrainingTypeService;
import com.accenture.ems.emstraining.model.TrainingType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

@WebMvcTest(TrainingTypeController.class)
@AutoConfigureMockMvc*/
public class TrainingTypeControllerTest {
   /* @Autowired
    MockMvc mockMvc;
    @MockBean
    TrainingTypeService TrainingTypeService;
    @Autowired
    ObjectMapper objectMapper;
    private TrainingType TrainingType;
    @BeforeEach
    void setUp(){
        TrainingType = new TrainingType();
        TrainingType.setId(1);
        TrainingType.setType("Test");
    }
    @Test
    void findAllTrainingTypes_returns200() throws Exception{
        when(TrainingTypeService.getAll()).thenReturn(Collections.singletonList(TrainingType));

        mockMvc.perform(get("/training")).andExpect(status().isOk());
    }
    @Test
    void getTrainingType_found_return200() throws Exception{
        when(TrainingTypeService.getTrainingTypeById(1)).thenReturn(Optional.of(TrainingType));

        mockMvc.perform(get("/training/1")).andExpect(status().isOk());
    }
    @Test
    void saveTrainingType_return200() throws Exception{
        doNothing().when(TrainingTypeService).saveTrainingType(any());

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TrainingType)))
                .andExpect(status().isOk());
    }
    @Test
    void updateTrainingType_return200() throws Exception{
        when(TrainingTypeService.updateTrainingType(eq(1),any())).thenReturn(TrainingType);

        mockMvc.perform(put("/training/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TrainingType)))
                .andExpect(status().isOk());
    }
    @Test
    void deleteTrainingType_return200() throws Exception{
        doNothing().when(TrainingTypeService).deleteTrainingType(1);

        mockMvc.perform(delete("/training/1")).andExpect(status().isOk());
    }*/
}
