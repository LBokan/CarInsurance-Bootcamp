package com.exadel.carinsurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotosResponseEntity {
  private Boolean isError;
  private String errorMessage;
  private String namesOfPhotosOfImpact;
}
