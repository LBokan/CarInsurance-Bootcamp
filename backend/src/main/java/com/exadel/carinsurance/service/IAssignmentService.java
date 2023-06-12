package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IAssignmentService {
  ResponseEntity createAssignment( AssignmentRequestEntity request );
}
