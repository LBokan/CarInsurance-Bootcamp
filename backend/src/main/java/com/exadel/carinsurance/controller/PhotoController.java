package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.service.IPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api" )
public class PhotoController {
  @Autowired
  private IPhotosService photosService;

  @GetMapping( "/user/photo/photosofimpact/{userId}/{assignmentId}/{photoName}" )
  public ResponseEntity getPhoto(
      @PathVariable( "userId" ) Long userId,
      @PathVariable( "assignmentId" ) Long assignmentId,
      @PathVariable( "photoName" ) String photoName
  ) {
    return photosService.getPhoto( userId, assignmentId, photoName );
  }
}
