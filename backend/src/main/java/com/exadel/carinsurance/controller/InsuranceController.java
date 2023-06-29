package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.model.request.*;
import com.exadel.carinsurance.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/insurance" )
public class InsuranceController {
  private final ICompanyService companyService;
  private final IAssignmentService assignmentService;
  private final IContactInfoService contactInfoService;
  private final IPhoneNumberService phoneNumberService;
  private final IAddressService addressService;
  private final ICommentService commentService;

  @Autowired
  public InsuranceController(
      ICompanyService companyService,
      IAssignmentService assignmentService,
      IContactInfoService contactInfoService,
      IPhoneNumberService phoneNumberService,
      IAddressService addressService,
      ICommentService commentService
  ) {
    this.companyService = companyService;
    this.assignmentService = assignmentService;
    this.contactInfoService = contactInfoService;
    this.phoneNumberService = phoneNumberService;
    this.addressService = addressService;
    this.commentService = commentService;
  }

  //  Company controllers
  @GetMapping( "/repairFacility" )
  public ResponseEntity getRepairFacilities() {
    return companyService.getRepairFacilities();
  }

  //  Assignment controllers
  @PutMapping( "/assignment/{assignmentId}" )
  public ResponseEntity setAssignmentRepairFacility(
      @PathVariable( "assignmentId" ) Long assignmentId,
      @RequestBody CompanyRequestEntity request
  ) {
    return assignmentService.setAssignmentRepairFacility( assignmentId, request );
  }

  //  Contact info controllers
  @PostMapping( "/contact" )
  public ResponseEntity createContactInfo( @RequestBody ContactInfoRequestEntity request ) {
    return contactInfoService.createContactInfo( request );
  }

  @PutMapping( "/contact/{contactInfoId}" )
  public ResponseEntity editContactInfo(
      @PathVariable( "contactInfoId" ) Long contactInfoId,
      @RequestBody ContactInfoRequestEntity request
  ) {
    return contactInfoService.editContactInfo( contactInfoId, request );
  }

  @DeleteMapping( "/contact/{contactInfoId}" )
  public ResponseEntity deleteContactInfo( @PathVariable( "contactInfoId" ) Long contactInfoId ) {
    return contactInfoService.deleteContactInfo( contactInfoId );
  }

  //  Phone number controllers
  @PostMapping( "/phoneNumber" )
  public ResponseEntity createPhoneNumber( @RequestBody PhoneNumberRequestEntity request ) {
    return phoneNumberService.createPhoneNumber( request );
  }

  @PutMapping( "/phoneNumber/{phoneNumberId}" )
  public ResponseEntity editPhoneNumber(
      @PathVariable( "phoneNumberId" ) Long phoneNumberId,
      @RequestBody PhoneNumberRequestEntity request
  ) {
    return phoneNumberService.editPhoneNumber( phoneNumberId, request );
  }

  @DeleteMapping( "/phoneNumber/{phoneNumberId}" )
  public ResponseEntity deletePhoneNumber( @PathVariable( "phoneNumberId" ) Long phoneNumberId ) {
    return phoneNumberService.deletePhoneNumber( phoneNumberId );
  }

  //  Address controllers
  @PostMapping( "/address" )
  public ResponseEntity createAddress( @RequestBody AddressRequestEntity request ) {
    return addressService.createAddress( request );
  }

  @PutMapping( "/address/{addressId}" )
  public ResponseEntity editAddress(
      @PathVariable( "addressId" ) Long addressId,
      @RequestBody AddressRequestEntity request
  ) {
    return addressService.editAddress( addressId, request );
  }

  @DeleteMapping( "/address/{addressId}" )
  public ResponseEntity deleteAddress( @PathVariable( "addressId" ) Long addressId ) {
    return addressService.deleteAddress( addressId );
  }

  //  Comment controllers
  @PostMapping( "/{assignmentId}/comment" )
  public ResponseEntity createComment(
      @PathVariable( "assignmentId" ) Long assignmentId,
      @RequestBody CommentRequestEntity request
  ) {
    return commentService.createComment( assignmentId, request );
  }
}
