package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoRequestEntity {
  private String type;
  private String firstName;
  private String lastName;
  private String email;
  private List<PhoneNumberRequestEntity> phoneNumbers;
  private List<AddressRequestEntity> addresses;
}
