package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1" )
public class GeneralController {
  private final ICompanyService companyService;
  private final ITypeService typeService;
  private final IAssignmentService assignmentService;
  private final IPhotosService photosService;
  private final ICommentService commentService;

  @Autowired
  public GeneralController(
      ICompanyService companyService,
      ITypeService typeService,
      IAssignmentService assignmentService,
      IPhotosService photosService,
      ICommentService commentService
  ) {
    this.companyService = companyService;
    this.typeService = typeService;
    this.assignmentService = assignmentService;
    this.photosService = photosService;
    this.commentService = commentService;
  }

  //  Type controllers
  @GetMapping( "/contactInfoType" )
  public ResponseEntity getContactInfoTypes() {
    return typeService.getContactInfoTypes();
  }

  @GetMapping( "/phoneNumberType" )
  public ResponseEntity getPhoneNumberTypes() {
    return typeService.getPhoneNumberTypes();
  }

  @GetMapping( "/addressType" )
  public ResponseEntity getAddressTypes() {
    return typeService.getAddressTypes();
  }

  //  Assignment controllers
  @GetMapping( "/assignment" )
  public ResponseEntity getAssignments() {
    return assignmentService.getAssignments();
  }

  @GetMapping( "/assignment/{assignmentId}" )
  public ResponseEntity getAssignment( @PathVariable( "assignmentId" ) Long assignmentId ) {
    return assignmentService.getAssignment( assignmentId );
  }

  //  Photo controllers
  @GetMapping( "photo/photosofimpact/{userId}/{assignmentId}/{photoName}" )
  public ResponseEntity getPhoto(
      @PathVariable( "userId" ) Long userId,
      @PathVariable( "assignmentId" ) Long assignmentId,
      @PathVariable( "photoName" ) String photoName
  ) {
    return photosService.getPhoto( userId, assignmentId, photoName );
  }

  //  Comment controllers
  @GetMapping( "/{assignmentId}/comment" )
  public ResponseEntity getAssignmentComments( @PathVariable( "assignmentId" ) Long assignmentId ) {
    return commentService.getAssignmentComments( assignmentId );
  }
}
