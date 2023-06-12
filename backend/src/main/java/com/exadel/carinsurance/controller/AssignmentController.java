package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api" )
public class AssignmentController {
  @Autowired
  private IAssignmentService assignmentService;

  @PostMapping( "/user/assignment" )
  public ResponseEntity signup(
      @RequestBody AssignmentRequestEntity request
  ) {
    return assignmentService.createAssignment( request );
  }
}