package com.accenture.ems.emstraining.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "training")
@Data
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    private Instant startDate;
    @Column(name = "end_date")
    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
}
