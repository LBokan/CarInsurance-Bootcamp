package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import com.exadel.carinsurance.repository.IAssignmentRepository;
import com.exadel.carinsurance.service.IPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhotosService implements IPhotosService {
  private final IAssignmentRepository assignmentRepository;

  @Value( "${directory.format}" )
  private String directoryFormat;

  @Autowired
  public PhotosService( IAssignmentRepository assignmentRepository ) {
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public PhotosResponseEntity savePhotos( Long userId, Long assignmentId, List<MultipartFile> photosOfImpact ) {
    List<String> namesOfPhotosOfImpactList = new ArrayList<>();

    try {
      String directoryPath = String
          .format( directoryFormat, userId, assignmentId );
      Path directory = Paths.get( directoryPath );

      if ( !Files.exists( directory ) ) {
        Files.createDirectories( directory );
      }

      for ( MultipartFile photo : photosOfImpact ) {
        String filename = photo.getOriginalFilename();

        namesOfPhotosOfImpactList.add( filename );

        Path filePath = directory.resolve( filename );
        photo.transferTo( filePath );
      }

    } catch ( Exception ex ) {
      return PhotosResponseEntity
          .builder()
          .isError( true )
          .errorMessage( "An error during photos saving" )
          .build();
    }

    return PhotosResponseEntity
        .builder()
        .isError( false )
        .namesOfPhotosOfImpact( String.join( ";", namesOfPhotosOfImpactList ) )
        .build();
  }

  @Override
  public ResponseEntity getPhoto( Long userId, Long assignmentId, String photoName ) {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ResponseEntity badRequestResponse = ResponseEntity
        .status( HttpStatus.BAD_REQUEST )
        .body( "Bad request" );

    Boolean hasClientRole = user.getRole().getName().equals( ERoleEntity.ROLE_CLIENT );
    Boolean hasInsuranceManagerRole = user.getRole().getName().equals( ERoleEntity.ROLE_INSURANCE_MANAGER );
    Boolean hasRepairManagerRole = user.getRole().getName().equals( ERoleEntity.ROLE_REPAIR_MANAGER );

    if ( hasClientRole && !user.getId().equals( userId ) ) {
      return badRequestResponse;
    } else if ( hasInsuranceManagerRole || hasRepairManagerRole ) {
      AssignmentEntity assignmentFromDB = assignmentRepository
          .findById( assignmentId )
          .orElseThrow( () ->
              new NotFoundException( "Assignments are not found" )
          );

      if ( hasInsuranceManagerRole && !assignmentFromDB.getInsuranceAgencyId().equals( user.getCompanyId() ) ) {
        return badRequestResponse;
      } else if ( hasRepairManagerRole && !assignmentFromDB.getRepairFacilityId().equals( user.getCompanyId() ) ) {
        return badRequestResponse;
      }
    }

    String directoryPath = String
        .format( directoryFormat, userId, assignmentId );
    Path imagePath = Paths.get( directoryPath, photoName );
    ByteArrayResource photo = null;

    try {
      photo = new ByteArrayResource( Files.readAllBytes( imagePath ) );
    } catch ( IOException e ) {
      return ResponseEntity
          .status( HttpStatus.NOT_FOUND )
          .body( "Photo is not found" );
    }

    String photoExtension = "";
    int dotIndex = photoName.lastIndexOf( "." );

    if ( dotIndex != -1 ) {
      photoExtension = photoName.substring( dotIndex + 1 );
    }

    MediaType mediaType = MediaType.IMAGE_JPEG;

    if ( "png".equalsIgnoreCase( photoExtension ) ) {
      mediaType = MediaType.IMAGE_PNG;
    }

    return ResponseEntity
        .ok()
        .contentType( mediaType )
        .body( photo );
  }
}
