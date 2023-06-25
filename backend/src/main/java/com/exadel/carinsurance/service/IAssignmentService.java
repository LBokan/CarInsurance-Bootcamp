package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.request.CompanyRequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAssignmentService {
  ResponseEntity getAssignments();

  ResponseEntity getAssignment( Long assignmentId );

  ResponseEntity createAssignment( AssignmentRequestEntity request, List<MultipartFile> photosOfImpact );

  ResponseEntity setAssignmentRepairFacility( Long assignmentId, CompanyRequestEntity request );

  AssignmentEntity checkAndSetAssignmentStatus( Long assignmentId );
}
