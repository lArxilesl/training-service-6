package com.accenture.ems.emstraining.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "training_id", nullable = false)
    private Long trainingId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "mentor_relation_id", nullable =false)
    private Long mentorRelationId;

    @Column(name = "final_presentation_date")
    private LocalDateTime finalPresentationDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
