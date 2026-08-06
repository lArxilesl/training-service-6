package com.accenture.ems.emstraining.business.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "training_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainingTypeDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    @NotNull
    private String type;
}
