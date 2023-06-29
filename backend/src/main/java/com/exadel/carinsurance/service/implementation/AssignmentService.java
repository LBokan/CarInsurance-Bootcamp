package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toEntity.*;
import com.exadel.carinsurance.mapper.toResponse.AssignmentResponseMapper;
import com.exadel.carinsurance.model.CompanyEntity;
import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.assignment.*;
import com.exadel.carinsurance.model.request.*;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;
import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import com.exadel.carinsurance.repository.*;
import com.exadel.carinsurance.service.IAssignmentService;
import com.exadel.carinsurance.service.IPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class AssignmentService implements IAssignmentService {
  private final EntityManager entityManager;
  private final IPhotosService photosService;
  private final IAssignmentStatusRepository assignmentStatusRepository;
  private final IAssignmentRepository assignmentRepository;
  private final IDirectionsOfImpactRepository directionsOfImpactRepository;
  private final IVehicleConditionInfoRepository vehicleConditionInfoRepository;
  private final IVehicleInfoRepository vehicleInfoRepository;
  private final IContactInfoTypeRepository contactInfoTypeRepository;
  private final IContactInfoRepository contactInfoRepository;
  private final IPhoneNumberTypeRepository phoneNumberTypeRepository;
  private final IPhoneNumberRepository phoneNumberRepository;
  private final IAddressTypeRepository addressTypeRepository;
  private final IAddressRepository addressRepository;
  private final ICompanyRepository companyRepository;
  private final AssignmentResponseMapper assignmentResponseMapper;
  private final AssignmentEntityMapper assignmentEntityMapper;
  private final VehicleInfoEntityMapper vehicleInfoEntityMapper;
  private final ContactInfoEntityMapper contactInfoEntityMapper;
  private final PhoneNumberEntityMapper phoneNumberEntityMapper;
  private final AddressEntityMapper addressEntityMapper;

  @Autowired
  public AssignmentService(
      EntityManager entityManager,
      IPhotosService photosService,
      IAssignmentStatusRepository assignmentStatusRepository,
      IAssignmentRepository assignmentRepository,
      IDirectionsOfImpactRepository directionsOfImpactRepository,
      IVehicleConditionInfoRepository vehicleConditionInfoRepository,
      IVehicleInfoRepository vehicleInfoRepository,
      IContactInfoTypeRepository contactInfoTypeRepository,
      IContactInfoRepository contactInfoRepository,
      IPhoneNumberTypeRepository phoneNumberTypeRepository,
      IPhoneNumberRepository phoneNumberRepository,
      IAddressTypeRepository addressTypeRepository,
      IAddressRepository addressRepository,
      ICompanyRepository companyRepository,
      AssignmentResponseMapper assignmentResponseMapper,
      AssignmentEntityMapper assignmentEntityMapper,
      VehicleInfoEntityMapper vehicleInfoEntityMapper,
      ContactInfoEntityMapper contactInfoEntityMapper,
      PhoneNumberEntityMapper phoneNumberEntityMapper,
      AddressEntityMapper addressEntityMapper
  ) {
    this.entityManager = entityManager;
    this.photosService = photosService;
    this.assignmentStatusRepository = assignmentStatusRepository;
    this.assignmentRepository = assignmentRepository;
    this.directionsOfImpactRepository = directionsOfImpactRepository;
    this.vehicleConditionInfoRepository = vehicleConditionInfoRepository;
    this.vehicleInfoRepository = vehicleInfoRepository;
    this.contactInfoTypeRepository = contactInfoTypeRepository;
    this.contactInfoRepository = contactInfoRepository;
    this.phoneNumberTypeRepository = phoneNumberTypeRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressTypeRepository = addressTypeRepository;
    this.addressRepository = addressRepository;
    this.companyRepository = companyRepository;
    this.assignmentResponseMapper = assignmentResponseMapper;
    this.assignmentEntityMapper = assignmentEntityMapper;
    this.vehicleInfoEntityMapper = vehicleInfoEntityMapper;
    this.contactInfoEntityMapper = contactInfoEntityMapper;
    this.phoneNumberEntityMapper = phoneNumberEntityMapper;
    this.addressEntityMapper = addressEntityMapper;
  }

  @Override
  public ResponseEntity getAssignments() {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<AssignmentEntity> assignmentsFromDB = new ArrayList<>();
    List<AssignmentResponseEntity> assignmentsResponse = new ArrayList<>();

    if ( user.getRole().getName().equals( ERoleEntity.ROLE_CLIENT ) ) {
      assignmentsFromDB = assignmentRepository
          .findAllByUserId( user.getId() )
          .orElseThrow( () ->
              new NotFoundException( "Assignments are not found" )
          );
    } else if ( user.getRole().getName().equals( ERoleEntity.ROLE_INSURANCE_MANAGER ) ) {
      assignmentsFromDB = assignmentRepository
          .findAllByInsuranceAgencyId( user.getCompanyId() )
          .orElseThrow( () ->
              new NotFoundException( "Assignments are not found" )
          );
    } else if ( user.getRole().getName().equals( ERoleEntity.ROLE_REPAIR_MANAGER ) ) {
      assignmentsFromDB = assignmentRepository
          .findAllByRepairFacilityId( user.getCompanyId() )
          .orElseThrow( () ->
              new NotFoundException( "Assignments are not found" )
          );
    }

    for ( AssignmentEntity assignment : assignmentsFromDB ) {
      assignmentsResponse.add( assignmentResponseMapper.toResponse( assignment ) );
    }

    Comparator<AssignmentResponseEntity> comparator = Comparator
        .comparing( AssignmentResponseEntity::getDateOfCreation )
        .reversed();
    Collections.sort( assignmentsResponse, comparator );

    return ResponseEntity
        .ok()
        .body( assignmentsResponse );
  }

  @Override
  public ResponseEntity getAssignment( Long assignmentId ) {
    AssignmentEntity assignmentFromDB = assignmentRepository
        .findById( assignmentId )
        .orElseThrow( () ->
            new NotFoundException( "The assignment is not found" )
        );

    return ResponseEntity
        .ok()
        .body( assignmentResponseMapper.toResponse( assignmentFromDB ) );
  }

  @Override
  public ResponseEntity createAssignment( AssignmentRequestEntity request, List<MultipartFile> photosOfImpact ) {
    //  Assignment creation
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserEntity mergedUser = entityManager.merge( user );

    LocalDateTime currentDateTimeAssignment = LocalDateTime.now();

    AssignmentStatusEntity assignmentStatusFromDB = assignmentStatusRepository
        .findByName( EAssignmentStatusEntity.NEW )
        .orElseThrow( () ->
            new NotFoundException( "The assignment status is not found" )
        );

    CompanyEntity companyFromDB = companyRepository
        .findById( user.getCompanyId() )
        .orElseThrow( () ->
            new NotFoundException( "The company is not found" )
        );

    AssignmentEntity assignment = assignmentEntityMapper.toEntity( request );
    assignment.setUser( mergedUser );
    assignment.setDateOfCreation( currentDateTimeAssignment );
    assignment.setStatus( assignmentStatusFromDB );
    assignment.setInsuranceAgency( companyFromDB );

    AssignmentEntity assignmentFromDB = assignmentRepository.save( assignment );

    //  Vehicle condition info creation
    PhotosResponseEntity photosResponse = photosService.savePhotos(
        user.getId(),
        assignmentFromDB.getId(),
        photosOfImpact
    );

    if ( photosResponse.getIsError() ) {
      return ResponseEntity
          .status( HttpStatus.INTERNAL_SERVER_ERROR )
          .body( photosResponse.getErrorMessage() );
    }

    DirectionOfImpactEntity directionOfImpactFromDB = directionsOfImpactRepository
        .findByName( request.getVehicleConditionInfo().getDirectionOfImpact() )
        .orElseThrow( () ->
            new NotFoundException( "The direction of impact is not found" )
        );

    VehicleConditionInfoEntity vehicleConditionInfo = VehicleConditionInfoEntity
        .builder()
        .directionOfImpact( directionOfImpactFromDB )
        .namesOfPhotosOfImpact( photosResponse.getNamesOfPhotosOfImpact() )
        .assignment( assignmentFromDB )
        .build();

    vehicleConditionInfoRepository.save( vehicleConditionInfo );

    //  Vehicle condition info creation
    VehicleInfoEntity vehicleInfo = vehicleInfoEntityMapper.toEntity( request );
    vehicleInfo.setAssignment( assignmentFromDB );

    vehicleInfoRepository.save( vehicleInfo );

    //  Contacts info creation
    for ( ContactInfoRequestEntity contactInfoRequest : request.getContactsInfo() ) {
      ContactInfoTypeEntity contactInfoTypeFromDB = contactInfoTypeRepository
          .findByName( contactInfoRequest.getType() )
          .orElseThrow( () ->
              new NotFoundException( "The contact information type is not found" )
          );

      ContactInfoEntity contactInfo = contactInfoEntityMapper.toEntity( contactInfoRequest );
      contactInfo.setAssignment( assignmentFromDB );
      contactInfo.setType( contactInfoTypeFromDB );

      ContactInfoEntity contactInfoFromDB = contactInfoRepository.save( contactInfo );

      for ( PhoneNumberRequestEntity phoneNumberRequest : contactInfoRequest.getPhoneNumbers() ) {
        PhoneNumberTypeEntity phoneNumberTypeFromDB = phoneNumberTypeRepository
            .findByName( phoneNumberRequest.getType() )
            .orElseThrow( () ->
                new NotFoundException( "The phone number type is not found" )
            );

        PhoneNumberEntity phoneNumber = phoneNumberEntityMapper.toEntity( phoneNumberRequest );
        phoneNumber.setContactInfo( contactInfoFromDB );
        phoneNumber.setType( phoneNumberTypeFromDB );

        phoneNumberRepository.save( phoneNumber );
      }

      for ( AddressRequestEntity addressRequest : contactInfoRequest.getAddresses() ) {
        AddressTypeEntity addressTypeFromDB = addressTypeRepository
            .findByName( addressRequest.getType() )
            .orElseThrow( () ->
                new NotFoundException( "The address type is not found" )
            );

        AddressEntity address = addressEntityMapper.toEntity( addressRequest );
        address.setContactInfo( contactInfoFromDB );
        address.setType( addressTypeFromDB );

        addressRepository.save( address );
      }
    }

    return ResponseEntity
        .ok()
        .body( assignmentFromDB.getId() );
  }

  @Override
  public ResponseEntity setAssignmentRepairFacility( Long assignmentId, CompanyRequestEntity request ) {
    CompanyEntity companyFromDB = companyRepository
        .findByName( request.getName() )
        .orElseThrow( () ->
            new NotFoundException( "The company is not found" )
        );

    AssignmentStatusEntity assignmentStatusFromDB = assignmentStatusRepository
        .findByName( EAssignmentStatusEntity.ASSIGNED )
        .orElseThrow( () ->
            new NotFoundException( "The assignment status is not found" )
        );

    AssignmentEntity assignmentFromDB = assignmentRepository
        .findById( assignmentId )
        .orElseThrow( () ->
            new NotFoundException( "The assignment is not found" )
        );

    assignmentFromDB.setStatus( assignmentStatusFromDB );
    assignmentFromDB.setRepairFacility( companyFromDB );

    AssignmentEntity assignmentEditedFromDB = assignmentRepository.save( assignmentFromDB );

    return ResponseEntity
        .ok()
        .body( assignmentResponseMapper.toResponse( assignmentEditedFromDB ) );
  }

  @Override
  public AssignmentEntity checkAndSetAssignmentStatus( Long assignmentId ) {
    AssignmentStatusEntity assignmentStatusNewFromDB = assignmentStatusRepository
        .findByName( EAssignmentStatusEntity.NEW )
        .orElseThrow( () ->
            new NotFoundException( "The assignment status is not found" )
        );

    AssignmentStatusEntity assignmentStatusInProgressFromDB = assignmentStatusRepository
        .findByName( EAssignmentStatusEntity.IN_PROGRESS )
        .orElseThrow( () ->
            new NotFoundException( "The assignment status is not found" )
        );

    AssignmentEntity assignmentFromDB = assignmentRepository
        .findById( assignmentId )
        .orElseThrow( () ->
            new NotFoundException( "The assignment is not found" )
        );

    if ( assignmentStatusNewFromDB.getId() == assignmentFromDB.getStatusId() ) {
      assignmentFromDB.setStatus( assignmentStatusInProgressFromDB );
    }

    AssignmentEntity assignmentWithNewStatusFromDB = assignmentRepository
        .save( assignmentFromDB );

    return assignmentWithNewStatusFromDB;
  }
}
