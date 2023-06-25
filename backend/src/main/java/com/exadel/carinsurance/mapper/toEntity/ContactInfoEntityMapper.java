package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoEntityMapper implements IEntityMapper<ContactInfoEntity, ContactInfoRequestEntity> {
  @Override
  public ContactInfoEntity toEntity( ContactInfoRequestEntity request ) {
    return ContactInfoEntity
        .builder()
        .firstName( request.getFirstName() )
        .lastName( request.getLastName() )
        .email( request.getEmail() )
        .build();
  }
}

