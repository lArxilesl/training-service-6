package com.accenture.ems.emstraining.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponseDTO {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private TrainingTypeResponseDTO trainingType;
}