package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {
  Optional<List<CommentEntity>> findAllByAssignmentId( Long assignmentId );

  Optional<CommentEntity> findByDateOfCreation( LocalDateTime date );
}
