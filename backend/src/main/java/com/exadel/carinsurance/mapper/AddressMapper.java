package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;

public class AddressMapper {
  public static AddressEntity mapToAddress( AddressRequestEntity addressRequest ) {
    return AddressEntity
        .builder()
        .type( addressRequest.getType() )
        .city( addressRequest.getCity() )
        .state( addressRequest.getState() )
        .zip( addressRequest.getZip() )
        .addressLine( addressRequest.getAddressLine() )
        .build();
  }
}
