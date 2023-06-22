package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
  Optional<List<AssignmentEntity>> findAllByUserId( Long userId );

  Optional<AssignmentEntity> findByDateOfCreation( LocalDateTime date );
}
