package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper implements IMapper<PhoneNumberEntity, PhoneNumberRequestEntity, PhoneNumberResponseEntity> {
  @Override
  public PhoneNumberEntity toEntity( PhoneNumberRequestEntity request ) {
    return PhoneNumberEntity
        .builder()
        .type( request.getType() )
        .number( request.getNumber() )
        .build();
  }

  @Override
  public PhoneNumberResponseEntity toResponse( PhoneNumberEntity entity ) {
    return PhoneNumberResponseEntity
        .builder()
        .id( entity.getId() )
        .type( entity.getType() )
        .number( entity.getNumber() )
        .build();
  }
}
