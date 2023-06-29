package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toEntity.ContactInfoEntityMapper;
import com.exadel.carinsurance.mapper.toResponse.ContactInfoResponseMapper;
import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoTypeEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.repository.IAssignmentRepository;
import com.exadel.carinsurance.repository.IContactInfoRepository;
import com.exadel.carinsurance.repository.IContactInfoTypeRepository;
import com.exadel.carinsurance.service.IAddressService;
import com.exadel.carinsurance.service.IAssignmentService;
import com.exadel.carinsurance.service.IContactInfoService;
import com.exadel.carinsurance.service.IPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ContactInfoService implements IContactInfoService {
  private final IAssignmentService assignmentService;
  private final IPhoneNumberService phoneNumberService;
  private final IAddressService addressService;
  private final IAssignmentRepository assignmentRepository;
  private final IContactInfoTypeRepository contactInfoTypeRepository;
  private final IContactInfoRepository contactInfoRepository;
  private final ContactInfoEntityMapper contactInfoEntityMapper;
  private final ContactInfoResponseMapper contactInfoResponseMapper;

  @Autowired
  public ContactInfoService(
      IAssignmentService assignmentService,
      IPhoneNumberService phoneNumberService,
      IAddressService addressService,
      IAssignmentRepository assignmentRepository,
      IContactInfoTypeRepository contactInfoTypeRepository,
      IContactInfoRepository contactInfoRepository,
      ContactInfoEntityMapper contactInfoEntityMapper,
      ContactInfoResponseMapper contactInfoResponseMapper
  ) {
    this.assignmentService = assignmentService;
    this.phoneNumberService = phoneNumberService;
    this.addressService = addressService;
    this.assignmentRepository = assignmentRepository;
    this.contactInfoTypeRepository = contactInfoTypeRepository;
    this.contactInfoRepository = contactInfoRepository;
    this.contactInfoEntityMapper = contactInfoEntityMapper;
    this.contactInfoResponseMapper = contactInfoResponseMapper;
  }

  @Override
  public ContactInfoEntity createContactInfoInDB( AssignmentEntity assignment, ContactInfoRequestEntity request ) {
    ContactInfoTypeEntity contactInfoTypeFromDB = contactInfoTypeRepository
        .findByName( request.getType() )
        .orElseThrow( () ->
            new NotFoundException( "The contact info type is not found" )
        );

    ContactInfoEntity contactInfo = contactInfoEntityMapper.toEntity( request );
    contactInfo.setType( contactInfoTypeFromDB );
    contactInfo.setAssignmentId( request.getAssignmentId() );
    contactInfo.setAssignment( assignment );

    ContactInfoEntity contactInfoFromDB = contactInfoRepository.save( contactInfo );

    for ( PhoneNumberRequestEntity phoneNumberRequest : request.getPhoneNumbers() ) {
      phoneNumberService.createPhoneNumberInDB( contactInfoFromDB, phoneNumberRequest );
    }

    for ( AddressRequestEntity addressRequest : request.getAddresses() ) {
      addressService.createAddressInDB( contactInfoFromDB, addressRequest );
    }

    return contactInfoFromDB;
  }

  @Override
  public ResponseEntity createContactInfo( ContactInfoRequestEntity request ) {
    AssignmentEntity assignmentFromDB = assignmentRepository
        .findById( request.getAssignmentId() )
        .orElseThrow( () ->
            new NotFoundException( "The assignment is not found" )
        );

    ContactInfoEntity contactInfoFromDB = createContactInfoInDB( assignmentFromDB, request );

    assignmentService.checkAndSetAssignmentStatus( contactInfoFromDB.getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( contactInfoFromDB.getId() );
  }

  @Override
  public ResponseEntity editContactInfo( Long contactInfoId, ContactInfoRequestEntity request ) {
    ContactInfoEntity contactInfoFromDB = contactInfoRepository
        .findById( contactInfoId )
        .orElseThrow( () ->
            new NotFoundException( "The contact information is not found" )
        );

    if ( !contactInfoFromDB.getType().getName().equals( request.getType() ) ) {
      ContactInfoTypeEntity contactInfoTypeFromDB = contactInfoTypeRepository
          .findByName( request.getType() )
          .orElseThrow( () ->
              new NotFoundException( "The contact information type is not found" )
          );

      contactInfoFromDB.setType( contactInfoTypeFromDB );
    }

    contactInfoFromDB.setFirstName( request.getFirstName() );
    contactInfoFromDB.setLastName( request.getLastName() );
    contactInfoFromDB.setEmail( request.getEmail() );

    ContactInfoEntity contactInfoEditedFromDB = contactInfoRepository.save( contactInfoFromDB );

    assignmentService.checkAndSetAssignmentStatus( contactInfoEditedFromDB.getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( contactInfoResponseMapper.toResponse( contactInfoEditedFromDB ) );
  }

  @Override
  public ResponseEntity deleteContactInfo( Long contactInfoId ) {
    ContactInfoEntity contactInfoFromDB = contactInfoRepository
        .findById( contactInfoId )
        .orElseThrow( () ->
            new NotFoundException( "The contact information is not found" )
        );

    contactInfoRepository.delete( contactInfoFromDB );

    assignmentService.checkAndSetAssignmentStatus( contactInfoFromDB.getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( contactInfoId );
  }
}
