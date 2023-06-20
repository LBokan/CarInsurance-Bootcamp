package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.*;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.assignment.*;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.model.response.AssignmentResponseEntity;
import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import com.exadel.carinsurance.repository.*;
import com.exadel.carinsurance.service.IAssignmentService;
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
  private final IUserRepository userRepository;
  private final IAssignmentStatusRepository assignmentStatusRepository;
  private final IAssignmentRepository assignmentRepository;
  private final IDirectionsOfImpactRepository directionsOfImpactRepository;
  private final IVehicleConditionInfoRepository vehicleConditionInfoRepository;
  private final IVehicleInfoRepository vehicleInfoRepository;
  private final IContactInfoRepository contactInfoRepository;
  private final IPhoneNumberRepository phoneNumberRepository;
  private final IAddressRepository addressRepository;
  private final PhotosService photosService;
  private final AssignmentMapper assignmentMapper;
  private final VehicleInfoMapper vehicleInfoMapper;
  private final ContactInfoMapper contactInfoMapper;
  private final PhoneNumberMapper phoneNumberMapper;
  private final AddressMapper addressMapper;

  @Autowired
  public AssignmentService(
      EntityManager entityManager,
      IUserRepository userRepository, IAssignmentStatusRepository assignmentStatusRepository,
      IAssignmentRepository assignmentRepository,
      IDirectionsOfImpactRepository directionsOfImpactRepository,
      IVehicleConditionInfoRepository vehicleConditionInfoRepository,
      IVehicleInfoRepository vehicleInfoRepository,
      IContactInfoRepository contactInfoRepository,
      IPhoneNumberRepository phoneNumberRepository,
      IAddressRepository addressRepository,
      PhotosService photosService,
      AssignmentMapper assignmentMapper,
      VehicleInfoMapper vehicleInfoMapper,
      ContactInfoMapper contactInfoMapper,
      PhoneNumberMapper phoneNumberMapper,
      AddressMapper addressMapper
  ) {
    this.entityManager = entityManager;
    this.userRepository = userRepository;
    this.assignmentStatusRepository = assignmentStatusRepository;
    this.assignmentRepository = assignmentRepository;
    this.directionsOfImpactRepository = directionsOfImpactRepository;
    this.vehicleConditionInfoRepository = vehicleConditionInfoRepository;
    this.vehicleInfoRepository = vehicleInfoRepository;
    this.contactInfoRepository = contactInfoRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressRepository = addressRepository;
    this.photosService = photosService;
    this.assignmentMapper = assignmentMapper;
    this.vehicleInfoMapper = vehicleInfoMapper;
    this.contactInfoMapper = contactInfoMapper;
    this.phoneNumberMapper = phoneNumberMapper;
    this.addressMapper = addressMapper;
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

    AssignmentEntity assignmentEntity = assignmentMapper.toEntity( request );
    assignmentEntity.setUser( mergedUser );
    assignmentEntity.setDateOfCreation( currentDateTimeAssignment );
    assignmentEntity.setStatus( assignmentStatusFromDB );

    assignmentRepository.save( assignmentEntity );

    AssignmentEntity assignmentEntityFromDB =
        assignmentRepository
            .findByDateOfCreation( currentDateTimeAssignment )
            .orElseThrow( () ->
                new NotFoundException( "The assignment is not found" )
            );

    //  Vehicle condition info creation
    PhotosResponseEntity photosResponseEntity = photosService.savePhotos(
        user.getUserId(),
        assignmentEntityFromDB.getAssignmentId(),
        photosOfImpact
    );

    if ( photosResponseEntity.getIsError() ) {
      return ResponseEntity
          .status( HttpStatus.INTERNAL_SERVER_ERROR )
          .body( photosResponseEntity.getErrorMessage() );
    }

    DirectionOfImpactEntity directionOfImpactFromDB = directionsOfImpactRepository
        .findByName( request.getVehicleConditionInfo().getDirectionOfImpact() )
        .orElseThrow( () ->
            new NotFoundException( "The direction of impact is not found" )
        );

    VehicleConditionInfoEntity vehicleConditionInfoEntity =
        VehicleConditionInfoEntity
            .builder()
            .directionOfImpact( directionOfImpactFromDB )
            .namesOfPhotosOfImpact( photosResponseEntity.getNamesOfPhotosOfImpact() )
            .assignment( assignmentEntityFromDB )
            .build();

    vehicleConditionInfoRepository.save( vehicleConditionInfoEntity );

    //  Vehicle condition info creation
    VehicleInfoEntity vehicleInfoEntity = vehicleInfoMapper.toEntity( request );
    vehicleInfoEntity.setAssignment( assignmentEntityFromDB );

    vehicleInfoRepository.save( vehicleInfoEntity );

    //  Contacts info creation
    for ( ContactInfoRequestEntity contactInfoRequest : request.getContactsInfo() ) {
      LocalDateTime currentDateTimeContactInfo = LocalDateTime.now();

      ContactInfoEntity contactInfoEntity = contactInfoMapper.toEntity( contactInfoRequest );
      contactInfoEntity.setDateOfCreation( currentDateTimeContactInfo );
      contactInfoEntity.setAssignment( assignmentEntityFromDB );

      contactInfoRepository.save( contactInfoEntity );

      ContactInfoEntity contactInfoEntityFromDB =
          contactInfoRepository
              .findByDateOfCreation( currentDateTimeContactInfo )
              .orElseThrow( () ->
                  new NotFoundException( "The contact information is not found" )
              );

      for ( PhoneNumberRequestEntity phoneNumberRequest : contactInfoRequest.getPhoneNumbers() ) {
        PhoneNumberEntity phoneNumberEntity = phoneNumberMapper.toEntity( phoneNumberRequest );
        phoneNumberEntity.setContactInfo( contactInfoEntityFromDB );

        phoneNumberRepository.save( phoneNumberEntity );
      }

      for ( AddressRequestEntity addressRequest : contactInfoRequest.getAddresses() ) {
        AddressEntity addressEntity = addressMapper.toEntity( addressRequest );
        addressEntity.setContactInfo( contactInfoEntityFromDB );

        addressRepository.save( addressEntity );
      }
    }

    return ResponseEntity
        .ok()
        .body( assignmentEntityFromDB.getAssignmentId() );
  }

  @Override
  public ResponseEntity getAssignments() {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<AssignmentResponseEntity> assignmentsResponse = new ArrayList<>();

    List<AssignmentEntity> assignmentsFromDB = assignmentRepository
        .findAllByUserId( user.getUserId() )
        .orElseThrow( () ->
            new NotFoundException( "Assignments are not found" )
        );

    for ( AssignmentEntity assignment : assignmentsFromDB ) {
      assignmentsResponse.add( assignmentMapper.toResponse( assignment ) );
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
        .body( assignmentMapper.toResponse( assignmentFromDB ) );
  }
}
