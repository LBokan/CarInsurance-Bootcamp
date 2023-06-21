package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.AssignmentStatusEntity;
import com.exadel.carinsurance.model.assignment.EAssignmentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAssignmentStatusRepository extends JpaRepository<AssignmentStatusEntity, Integer> {
  Optional<AssignmentStatusEntity> findByName( EAssignmentStatusEntity name );
}
