package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import com.exadel.carinsurance.service.IPhotosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
  @Value( "${directory.format}" )
  private String directoryFormat;

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
  public ResponseEntity getPhoto(
      @PathVariable( "userId" ) Long userId,
      @PathVariable( "assignmentId" ) Long assignmentId,
      @PathVariable( "photoName" ) String photoName
  ) {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if ( !user.getUserId().equals( userId ) ) {
      return ResponseEntity
          .status( HttpStatus.BAD_REQUEST )
          .body( "Bad request" );
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
