package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toEntity.PhoneNumberEntityMapper;
import com.exadel.carinsurance.mapper.toResponse.PhoneNumberResponseMapper;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberTypeEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.repository.IContactInfoRepository;
import com.exadel.carinsurance.repository.IPhoneNumberRepository;
import com.exadel.carinsurance.repository.IPhoneNumberTypeRepository;
import com.exadel.carinsurance.service.IAssignmentService;
import com.exadel.carinsurance.service.IPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PhoneNumberService implements IPhoneNumberService {
  private final IAssignmentService assignmentService;
  private final IContactInfoRepository contactInfoRepository;
  private final IPhoneNumberTypeRepository phoneNumberTypeRepository;
  private final IPhoneNumberRepository phoneNumberRepository;
  private final PhoneNumberEntityMapper phoneNumberEntityMapper;
  private final PhoneNumberResponseMapper phoneNumberResponseMapper;

  @Autowired
  public PhoneNumberService(
      IAssignmentService assignmentService,
      IContactInfoRepository contactInfoRepository,
      IPhoneNumberTypeRepository phoneNumberTypeRepository,
      IPhoneNumberRepository phoneNumberRepository,
      PhoneNumberEntityMapper phoneNumberEntityMapper,
      PhoneNumberResponseMapper phoneNumberResponseMapper
  ) {
    this.assignmentService = assignmentService;
    this.contactInfoRepository = contactInfoRepository;
    this.phoneNumberTypeRepository = phoneNumberTypeRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.phoneNumberEntityMapper = phoneNumberEntityMapper;
    this.phoneNumberResponseMapper = phoneNumberResponseMapper;
  }

  @Override
  public PhoneNumberEntity createPhoneNumberInDB( ContactInfoEntity contactInfo, PhoneNumberRequestEntity request ) {
    PhoneNumberTypeEntity phoneNumberTypeFromDB = phoneNumberTypeRepository
        .findByName( request.getType() )
        .orElseThrow( () ->
            new NotFoundException( "The phone number type is not found" )
        );

    PhoneNumberEntity phoneNumber = phoneNumberEntityMapper.toEntity( request );
    phoneNumber.setType( phoneNumberTypeFromDB );
    phoneNumber.setContactInfo( contactInfo );

    PhoneNumberEntity phoneNumberFromDB = phoneNumberRepository.save( phoneNumber );

    return phoneNumberFromDB;
  }

  @Override
  public ResponseEntity createPhoneNumber( PhoneNumberRequestEntity request ) {
    ContactInfoEntity contactInfoFromDB = contactInfoRepository
        .findById( request.getContactInfoId() )
        .orElseThrow( () ->
            new NotFoundException( "The contact information is not found" )
        );

    PhoneNumberEntity phoneNumberFromDB = createPhoneNumberInDB( contactInfoFromDB, request );

    assignmentService.checkAndSetAssignmentStatus( phoneNumberFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( phoneNumberFromDB.getId() );
  }

  @Override
  public ResponseEntity editPhoneNumber( Long phoneNumberId, PhoneNumberRequestEntity request ) {
    PhoneNumberEntity phoneNumberFromDB = phoneNumberRepository
        .findById( phoneNumberId )
        .orElseThrow( () ->
            new NotFoundException( "The phone number is not found" )
        );

    if ( !phoneNumberFromDB.getType().getName().equals( request.getType() ) ) {
      PhoneNumberTypeEntity phoneNumberTypeFromDB = phoneNumberTypeRepository
          .findByName( request.getType() )
          .orElseThrow( () ->
              new NotFoundException( "The phone number type is not found" )
          );

      phoneNumberFromDB.setType( phoneNumberTypeFromDB );
    }

    phoneNumberFromDB.setNumber( request.getNumber() );

    PhoneNumberEntity phoneNumberEditedFromDB = phoneNumberRepository.save( phoneNumberFromDB );

    assignmentService.checkAndSetAssignmentStatus( phoneNumberEditedFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( phoneNumberResponseMapper.toResponse( phoneNumberEditedFromDB ) );
  }

  @Override
  public ResponseEntity deletePhoneNumber( Long phoneNumberId ) {
    PhoneNumberEntity phoneNumberFromDB = phoneNumberRepository
        .findById( phoneNumberId )
        .orElseThrow( () ->
            new NotFoundException( "The phone number is not found" )
        );

    phoneNumberRepository.delete( phoneNumberFromDB );

    assignmentService.checkAndSetAssignmentStatus( phoneNumberFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( phoneNumberId );
  }
}
