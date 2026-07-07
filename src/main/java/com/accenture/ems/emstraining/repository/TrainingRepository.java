package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
//    @Query("SELECT t FROM Training t JOIN FETCH t.trainingType")
//    List<Training> findAllWithType();
//
//    @Query("SELECT t FROM Training t JOIN FETCH t.trainingType WHERE t.id=:id")
//    Optional<Training> findByIdWithType(@Param("id") Long id);

}