package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toEntity.CommentEntityMapper;
import com.exadel.carinsurance.mapper.toResponse.CommentResponseMapper;
import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.CommentEntity;
import com.exadel.carinsurance.model.request.CommentRequestEntity;
import com.exadel.carinsurance.model.response.CommentResponseEntity;
import com.exadel.carinsurance.repository.ICommentRepository;
import com.exadel.carinsurance.service.IAssignmentService;
import com.exadel.carinsurance.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CommentService implements ICommentService {
  private final IAssignmentService assignmentService;
  private final ICommentRepository commentRepository;
  private final CommentResponseMapper commentResponseMapper;
  private final CommentEntityMapper commentEntityMapper;

  @Autowired
  public CommentService(
      IAssignmentService assignmentService,
      ICommentRepository commentRepository,
      CommentResponseMapper commentResponseMapper,
      CommentEntityMapper commentEntityMapper
  ) {
    this.assignmentService = assignmentService;
    this.commentRepository = commentRepository;
    this.commentResponseMapper = commentResponseMapper;
    this.commentEntityMapper = commentEntityMapper;
  }

  @Override
  public ResponseEntity getAssignmentComments( Long assignmentId ) {
    List<CommentEntity> commentsFromDB = commentRepository
        .findAllByAssignmentId( assignmentId )
        .orElseThrow( () ->
            new NotFoundException( "Comments are not found" )
        );

    List<CommentResponseEntity> commentsResponse = new ArrayList<>();

    for ( CommentEntity comment : commentsFromDB ) {
      commentsResponse.add( commentResponseMapper.toResponse( comment ) );
    }

    Comparator<CommentResponseEntity> comparator = Comparator
        .comparing( CommentResponseEntity::getDateOfCreation )
        .reversed();
    Collections.sort( commentsResponse, comparator );

    return ResponseEntity
        .ok()
        .body( commentsResponse );
  }

  @Override
  public ResponseEntity createComment( Long assignmentId, CommentRequestEntity request ) {
    LocalDateTime currentDateTimeComment = LocalDateTime.now();

    AssignmentEntity assignmentWithNewStatusFromDB = assignmentService
        .checkAndSetAssignmentStatus( assignmentId );

    CommentEntity comment = commentEntityMapper.toEntity( request );
    comment.setDateOfCreation( currentDateTimeComment );
    comment.setAssignment( assignmentWithNewStatusFromDB );

    commentRepository.save( comment );

    CommentEntity commentFromDB = commentRepository
        .findByDateOfCreation( currentDateTimeComment )
        .orElseThrow( () ->
            new NotFoundException( "The comment is not found" )
        );

    return ResponseEntity
        .ok()
        .body( commentFromDB.getId() );
  }

  @Override
  public ResponseEntity setIsCommentRead( Long assignmentId, Long commentId ) {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if ( user.getRole().getName().equals( ERoleEntity.ROLE_REPAIR_MANAGER ) ) {
      CommentEntity commentFromDB = commentRepository
          .findById( commentId )
          .orElseThrow( () ->
              new NotFoundException( "The comment is not found" )
          );

      if ( assignmentId.equals( commentFromDB.getAssignmentId() ) ) {
        commentFromDB.setIsRead( 1 );
        commentRepository.save( commentFromDB );
      }
    }

    return ResponseEntity
        .ok()
        .body( commentId );
  }
}
