package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.AddressMapper;
import com.exadel.carinsurance.mapper.ContactInfoMapper;
import com.exadel.carinsurance.mapper.VehicleInfoMapper;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.assignment.*;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.model.request.AssignmentRequestEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.repository.*;
import com.exadel.carinsurance.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AssignmentService implements IAssignmentService {
  private final EntityManager entityManager;
  private final IAssignmentRepository assignmentRepository;
  private final IDirectionsOfImpactRepository directionsOfImpactRepository;
  private final IVehicleConditionInfoRepository vehicleConditionInfoRepository;
  private final IVehicleInfoRepository vehicleInfoRepository;
  private final IContactInfoRepository contactInfoRepository;
  private final IPhoneNumberRepository phoneNumberRepository;
  private final IAddressRepository addressRepository;

  @Autowired
  public AssignmentService(
      EntityManager entityManager,
      IAssignmentRepository assignmentRepository,
      IDirectionsOfImpactRepository directionsOfImpactRepository,
      IVehicleConditionInfoRepository vehicleConditionInfoRepository,
      IVehicleInfoRepository vehicleInfoRepository,
      IContactInfoRepository contactInfoRepository,
      IPhoneNumberRepository phoneNumberRepository,
      IAddressRepository addressRepository
  ) {
    this.entityManager = entityManager;
    this.assignmentRepository = assignmentRepository;
    this.directionsOfImpactRepository = directionsOfImpactRepository;
    this.vehicleConditionInfoRepository = vehicleConditionInfoRepository;
    this.vehicleInfoRepository = vehicleInfoRepository;
    this.contactInfoRepository = contactInfoRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressRepository = addressRepository;
  }

  @Override
  public ResponseEntity createAssignment(
      @RequestPart( "assignment" ) AssignmentRequestEntity request,
      @RequestPart( "photosOfImpact" ) List<MultipartFile> photosOfImpact
  ) {
    //  Assignment creation
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    LocalDateTime currentDateTimeAssignment = LocalDateTime.now();

    AssignmentEntity assignmentEntity = AssignmentEntity
        .builder()
        .user( user )
        .dateOfCreation( currentDateTimeAssignment )
        .dateOfIncident( request.getDateOfIncident() )
        .build();
    AssignmentEntity mergedAssignment = entityManager.merge( assignmentEntity );

    assignmentRepository.save( mergedAssignment );

    AssignmentEntity assignmentEntityFromDB =
        assignmentRepository
            .findByDateOfCreation( currentDateTimeAssignment )
            .orElseThrow( () ->
                new NotFoundException( "The assignment is not found" )
            );

    //  Vehicle condition info creation
    String namesOfPhotosOfImpact = "";

    try {
      String directoryPath = String
          .format(
              "photosofimpact/userId=%s/assignmentId=%s",
              user.getUserId(), assignmentEntityFromDB.getAssignmentId()
          );
      Path directory = Paths.get( directoryPath );

      if ( !Files.exists( directory ) ) {
        Files.createDirectories( directory );
      }

      for ( MultipartFile photo : photosOfImpact ) {
        String filename = photo.getOriginalFilename();

        if ( "".equals( namesOfPhotosOfImpact ) ) {
          namesOfPhotosOfImpact = filename;
        } else {
          namesOfPhotosOfImpact = String.format( "%s;%s", namesOfPhotosOfImpact, filename );
        }

        Path filePath = directory.resolve( filename );
        photo.transferTo( filePath );
      }

    } catch ( Exception ex ) {
      return ResponseEntity
          .status( HttpStatus.INTERNAL_SERVER_ERROR )
          .body( "An error during photos saving" );
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
            .namesOfPhotosOfImpact( namesOfPhotosOfImpact )
            .assignment( assignmentEntityFromDB )
            .build();
    VehicleConditionInfoEntity mergedVehicleConditionInfo = entityManager.merge( vehicleConditionInfoEntity );

    vehicleConditionInfoRepository.save( mergedVehicleConditionInfo );

    //  Vehicle condition info creation
    VehicleInfoEntity vehicleInfoEntity =
        VehicleInfoMapper.mapToVehicleInfo( request );
    vehicleInfoEntity.setAssignment( assignmentEntityFromDB );
    VehicleInfoEntity mergedVehicleInfo = entityManager.merge( vehicleInfoEntity );

    vehicleInfoRepository.save( mergedVehicleInfo );

    //  Contacts info creation
    for ( ContactInfoRequestEntity contactInfoRequest : request.getContactsInfo() ) {
      LocalDateTime currentDateTimeContactInfo = LocalDateTime.now();

      ContactInfoEntity contactInfoEntity = ContactInfoMapper.mapToContactInfo( contactInfoRequest );
      contactInfoEntity.setDateOfCreation( currentDateTimeContactInfo );
      contactInfoEntity.setAssignment( assignmentEntityFromDB );
      ContactInfoEntity mergedContactInfo = entityManager.merge( contactInfoEntity );

      contactInfoRepository.save( mergedContactInfo );

      ContactInfoEntity contactInfoEntityFromDB =
          contactInfoRepository
              .findByDateOfCreation( currentDateTimeContactInfo )
              .orElseThrow( () ->
                  new NotFoundException( "The contact information is not found" )
              );

      for ( PhoneNumberRequestEntity phoneNumberRequest : contactInfoRequest.getPhoneNumbers() ) {
        PhoneNumberEntity phoneNumberEntity = PhoneNumberEntity
            .builder()
            .type( phoneNumberRequest.getType() )
            .number( phoneNumberRequest.getNumber() )
            .contactInfo( contactInfoEntityFromDB )
            .build();
        PhoneNumberEntity mergedPhoneNumber = entityManager.merge( phoneNumberEntity );

        phoneNumberRepository.save( mergedPhoneNumber );
      }

      for ( AddressRequestEntity addressRequest : contactInfoRequest.getAddresses() ) {
        AddressEntity addressEntity = AddressMapper.mapToAddress( addressRequest );
        addressEntity.setContactInfo( contactInfoEntityFromDB );
        AddressEntity mergedAddress = entityManager.merge( addressEntity );

        addressRepository.save( mergedAddress );
      }
    }

    return ResponseEntity
        .ok( "An assignments is successfully created" );
  }
}
