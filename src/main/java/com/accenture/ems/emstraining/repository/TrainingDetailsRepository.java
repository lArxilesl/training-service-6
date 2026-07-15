package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.TrainingDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingDetailsRepository
        extends JpaRepository<TrainingDetailsEntity, Long> {
}