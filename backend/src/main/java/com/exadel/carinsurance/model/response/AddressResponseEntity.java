package com.exadel.carinsurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseEntity {
  private Long id;
  private String type;
  private String city;
  private String state;
  private String zip;
  private String addressLine;
}
