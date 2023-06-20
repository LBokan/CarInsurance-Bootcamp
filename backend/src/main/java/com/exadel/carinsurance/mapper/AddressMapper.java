package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapper<AddressEntity, AddressRequestEntity, AddressResponseEntity> {
  @Override
  public AddressEntity toEntity( AddressRequestEntity request ) {
    return AddressEntity
        .builder()
        .type( request.getType() )
        .city( request.getCity() )
        .state( request.getState() )
        .zip( request.getZip() )
        .addressLine( request.getAddressLine() )
        .build();
  }

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
