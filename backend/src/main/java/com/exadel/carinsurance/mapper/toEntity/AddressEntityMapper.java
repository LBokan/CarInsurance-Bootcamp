package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper implements IEntityMapper<AddressEntity, AddressRequestEntity> {
  @Override
  public AddressEntity toEntity( AddressRequestEntity request ) {
    return AddressEntity
        .builder()
        .city( request.getCity() )
        .state( request.getState() )
        .zip( request.getZip() )
        .addressLine( request.getAddressLine() )
        .build();
  }
}
