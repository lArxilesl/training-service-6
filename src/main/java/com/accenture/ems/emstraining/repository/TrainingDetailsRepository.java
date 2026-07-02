package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.model.entity.TrainingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingDetailsRepository extends JpaRepository<TrainingDetails, Long> {
    boolean existsByTrainingId(Long id);
}