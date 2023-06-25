package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1/repair" )
public class RepairController {
  private final ICommentService commentService;

  @Autowired
  public RepairController(
      ICommentService commentService
  ) {
    this.commentService = commentService;
  }

  //  Comment controllers
  @PutMapping( "/{assignmentId}/comment/{commentId}" )
  public ResponseEntity setIsCommentRead(
      @PathVariable( "assignmentId" ) Long assignmentId,
      @PathVariable( "commentId" ) Long commentId
  ) {
    return commentService.setIsCommentRead( assignmentId, commentId );
  }
}
