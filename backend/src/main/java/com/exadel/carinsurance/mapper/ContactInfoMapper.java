package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;

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

  public static ContactInfoResponseEntity mapToContactInfoResponse( ContactInfoEntity contactInfo ) {
    return ContactInfoResponseEntity
        .builder()
        .id( contactInfo.getId() )
        .type( contactInfo.getType() )
        .firstName( contactInfo.getFirstName() )
        .lastName( contactInfo.getLastName() )
        .email( contactInfo.getEmail() )
        .build();
  }
}

