package com.accenture.ems.emstraining.business.repository;

import com.accenture.ems.emstraining.business.repository.dao.TrainingTypeDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingTypeDAO,Integer> {
}
