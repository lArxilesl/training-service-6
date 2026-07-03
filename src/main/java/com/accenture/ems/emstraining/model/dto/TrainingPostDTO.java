package com.accenture.ems.emstraining.model.dto;

import com.accenture.ems.emstraining.validation.ValidDateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@ValidDateRange
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPostDTO {
    @NotEmpty(message = "Training name should not be empty")
    @Size(min = 1, max = 200, message = "Training name should be at least 1 character and at most 200 characters")
    private String name;
    @NotNull(message = "Training start date should not be empty")
    private Instant startDate;
    @NotNull(message = "Training end date should not be empty")
    private Instant endDate;
    @NotNull(message = "Training type should not be empty")
    private Long trainingTypeId;
}