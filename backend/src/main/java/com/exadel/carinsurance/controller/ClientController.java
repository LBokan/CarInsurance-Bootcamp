package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.service.IAssignmentService;
import com.exadel.carinsurance.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/client" )
public class ClientController {
  private final ICompanyService companyService;
  private final IAssignmentService assignmentService;

  @Autowired
  public ClientController(
      ICompanyService companyService,
      IAssignmentService assignmentService
  ) {
    this.companyService = companyService;
    this.assignmentService = assignmentService;
  }

  //  Company controllers
  @GetMapping( "/insuranceAgency" )
  public ResponseEntity getInsuranceAgencies() {
    return companyService.getInsuranceAgencies();
  }

  //  Assignment controller
  @PostMapping( "/assignment" )
  public ResponseEntity createAssignment(
      @RequestPart( "assignment" ) AssignmentRequestEntity request,
      @RequestPart( "photosOfImpact" ) List<MultipartFile> photosOfImpact
  ) {
    return assignmentService.createAssignment( request, photosOfImpact );
  }
}
