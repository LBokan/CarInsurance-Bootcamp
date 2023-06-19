package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAssignmentService {
  ResponseEntity createAssignment(
      AssignmentRequestEntity request,
      List<MultipartFile> photosOfImpact
  );

  ResponseEntity getAssignments();

  ResponseEntity getAssignment( Long assignmentId );
}
