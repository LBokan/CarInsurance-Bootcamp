package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;

public class ContactInfoMapper {
  public static ContactInfoEntity mapToContactInfo( ContactInfoRequestEntity contactInfoRequest ) {
    return ContactInfoEntity
        .builder()
        .type( contactInfoRequest.getType() )
        .firstName( contactInfoRequest.getFirstName() )
        .lastName( contactInfoRequest.getLastName() )
        .email( contactInfoRequest.getEmail() )
        .build();
  }
}
