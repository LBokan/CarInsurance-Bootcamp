package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ContactInfoResponseMapper implements IResponseMapper<ContactInfoEntity, ContactInfoResponseEntity> {
  private final PhoneNumberResponseMapper phoneNumberResponseMapper;
  private final AddressResponseMapper addressResponseMapper;

  @Autowired
  public ContactInfoResponseMapper(
      PhoneNumberResponseMapper phoneNumberResponseMapper,
      AddressResponseMapper addressResponseMapper
  ) {
    this.phoneNumberResponseMapper = phoneNumberResponseMapper;
    this.addressResponseMapper = addressResponseMapper;
  }

  @Override
  public ContactInfoResponseEntity toResponse( ContactInfoEntity entity ) {
    List<PhoneNumberResponseEntity> phoneNumbersResponse = new ArrayList<>();
    List<AddressResponseEntity> addressesResponse = new ArrayList<>();

    for ( PhoneNumberEntity phoneNumber : entity.getPhoneNumbers() ) {
      PhoneNumberResponseEntity phoneNumberResponse = phoneNumberResponseMapper.toResponse( phoneNumber );

      phoneNumbersResponse.add( phoneNumberResponse );
    }

    for ( AddressEntity address : entity.getAddresses() ) {
      AddressResponseEntity addressResponse = addressResponseMapper.toResponse( address );

      addressesResponse.add( addressResponse );
    }

    Comparator<PhoneNumberResponseEntity> comparatorPhoneNumber = Comparator
        .comparing( PhoneNumberResponseEntity::getId );
    Collections.sort( phoneNumbersResponse, comparatorPhoneNumber );

    Comparator<AddressResponseEntity> comparatorAddress = Comparator
        .comparing( AddressResponseEntity::getId );
    Collections.sort( addressesResponse, comparatorAddress );

    return ContactInfoResponseEntity
        .builder()
        .id( entity.getId() )
        .type( entity.getType().getName() )
        .firstName( entity.getFirstName() )
        .lastName( entity.getLastName() )
        .email( entity.getEmail() )
        .phoneNumbers( phoneNumbersResponse )
        .addresses( addressesResponse )
        .build();
  }
}

