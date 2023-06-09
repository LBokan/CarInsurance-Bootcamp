package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public class AssignmentController {
  @Autowired
  private IAssignmentService assignmentService;

  @PostMapping( "/user/assignment" )
  public ResponseEntity createAssignment(
      @RequestPart( "assignment" ) AssignmentRequestEntity request,
      @RequestPart( "photosOfImpact" ) List<MultipartFile> photosOfImpact
  ) {
    return assignmentService.createAssignment( request, photosOfImpact );
  }

  @GetMapping( "/user/assignment" )
  public ResponseEntity getAssignments() {
    return assignmentService.getAssignments();
  }

  @GetMapping( "/user/assignment/{assignmentId}" )
  public ResponseEntity getAssignment(
      @PathVariable( "assignmentId" ) Long assignmentId
  ) {
    return assignmentService.getAssignment( assignmentId );
  }
}
