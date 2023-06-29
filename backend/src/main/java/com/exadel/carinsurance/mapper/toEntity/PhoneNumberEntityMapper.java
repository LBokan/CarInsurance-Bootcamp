package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberEntityMapper implements IEntityMapper<PhoneNumberEntity, PhoneNumberRequestEntity> {
  @Override
  public PhoneNumberEntity toEntity( PhoneNumberRequestEntity request ) {
    return PhoneNumberEntity
        .builder()
        .number( request.getNumber() )
        .build();
  }
}
