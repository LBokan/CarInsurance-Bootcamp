package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberResponseMapper implements IResponseMapper<PhoneNumberEntity, PhoneNumberResponseEntity> {
  @Override
  public PhoneNumberResponseEntity toResponse( PhoneNumberEntity entity ) {
    return PhoneNumberResponseEntity
        .builder()
        .id( entity.getId() )
        .type( entity.getType().getName() )
        .number( entity.getNumber() )
        .build();
  }
}
