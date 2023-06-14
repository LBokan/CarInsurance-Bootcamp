package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.response.PhotosResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotosService {
  PhotosResponseEntity savePhotos(
      Long userId,
      Long assignmentId,
      List<MultipartFile> photosOfImpact
  );
}
