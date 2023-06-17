package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;

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

  public static AddressResponseEntity mapToAddressResponse( AddressEntity address ) {
    return AddressResponseEntity
        .builder()
        .id( address.getId() )
        .type( address.getType() )
        .city( address.getCity() )
        .state( address.getState() )
        .zip( address.getZip() )
        .addressLine( address.getAddressLine() )
        .build();
  }
}
