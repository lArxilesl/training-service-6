package com.accenture.ems.emstraining.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TrainingResponseDTO {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private TrainingTypeResponseDTO trainingType;
}