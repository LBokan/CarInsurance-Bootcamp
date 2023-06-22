package com.exadel.carinsurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoResponseEntity {
  private Long id;
  private String type;
  private String firstName;
  private String lastName;
  private String email;
  private List<PhoneNumberResponseEntity> phoneNumbers;
  private List<AddressResponseEntity> addresses;
}
