package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import com.exadel.carinsurance.service.IPhotosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
}
