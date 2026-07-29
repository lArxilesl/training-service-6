package com.accenture.ems.emstraining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDetailsDTO {

    private Long id;

    @NotNull
    @Positive
    private Long trainingId;

    @NotNull
    @Positive
    private Long employeeId;

    @NotNull
    @Positive
    private Long mentorRelationId;

    @NotNull
    private LocalDateTime finalPresentationDate;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;
}
