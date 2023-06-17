package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;

public class PhoneNumberMapper {
  public static PhoneNumberEntity mapToPhoneNumber( PhoneNumberRequestEntity phoneNumberRequest ) {
    return PhoneNumberEntity
        .builder()
        .type( phoneNumberRequest.getType() )
        .number( phoneNumberRequest.getNumber() )
        .build();
  }

  public static PhoneNumberResponseEntity mapToPhoneNumberResponse( PhoneNumberEntity phoneNumber ) {
    return PhoneNumberResponseEntity
        .builder()
        .id( phoneNumber.getId() )
        .type( phoneNumber.getType() )
        .number( phoneNumber.getNumber() )
        .build();
  }
}
