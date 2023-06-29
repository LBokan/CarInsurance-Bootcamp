package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestEntity {
  private String type;
  private String city;
  private String state;
  private String zip;
  private String addressLine;
  private Long contactInfoId;
}
