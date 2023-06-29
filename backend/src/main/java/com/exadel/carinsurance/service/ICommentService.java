package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.request.CommentRequestEntity;
import org.springframework.http.ResponseEntity;

public interface ICommentService {
  ResponseEntity getAssignmentComments( Long assignmentId );

  ResponseEntity createComment( Long assignmentId, CommentRequestEntity request );

  ResponseEntity setIsCommentRead( Long assignmentId, Long commentId );

}
