package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressResponseMapper implements IResponseMapper<AddressEntity, AddressResponseEntity> {
  @Override
  public AddressResponseEntity toResponse( AddressEntity entity ) {
    return AddressResponseEntity
        .builder()
        .id( entity.getId() )
        .type( entity.getType() )
        .city( entity.getCity() )
        .state( entity.getState() )
        .zip( entity.getZip() )
        .addressLine( entity.getAddressLine() )
        .build();
  }
}
