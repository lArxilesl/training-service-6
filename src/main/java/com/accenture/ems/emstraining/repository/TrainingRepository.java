package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
