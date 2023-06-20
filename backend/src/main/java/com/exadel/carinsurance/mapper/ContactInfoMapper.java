package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactInfoMapper implements IMapper<ContactInfoEntity, ContactInfoRequestEntity, ContactInfoResponseEntity> {
  private final PhoneNumberMapper phoneNumberMapper;
  private final AddressMapper addressMapper;

  @Autowired
  public ContactInfoMapper(
      PhoneNumberMapper phoneNumberMapper,
      AddressMapper addressMapper
  ) {
    this.phoneNumberMapper = phoneNumberMapper;
    this.addressMapper = addressMapper;
  }

  @Override
  public ContactInfoEntity toEntity( ContactInfoRequestEntity request ) {
    return ContactInfoEntity
        .builder()
        .type( request.getType() )
        .firstName( request.getFirstName() )
        .lastName( request.getLastName() )
        .email( request.getEmail() )
        .build();
  }

  @Override
  public ContactInfoResponseEntity toResponse( ContactInfoEntity entity ) {
    List<PhoneNumberResponseEntity> phoneNumbersResponse = new ArrayList<>();
    List<AddressResponseEntity> addressesResponse = new ArrayList<>();

    for ( PhoneNumberEntity phoneNumber : entity.getPhoneNumbers() ) {
      PhoneNumberResponseEntity phoneNumberResponse = phoneNumberMapper.toResponse( phoneNumber );

      phoneNumbersResponse.add( phoneNumberResponse );
    }

    for ( AddressEntity address : entity.getAddresses() ) {
      AddressResponseEntity addressResponse = addressMapper.toResponse( address );

      addressesResponse.add( addressResponse );
    }

    return ContactInfoResponseEntity
        .builder()
        .id( entity.getId() )
        .type( entity.getType() )
        .firstName( entity.getFirstName() )
        .lastName( entity.getLastName() )
        .email( entity.getEmail() )
        .phoneNumbers( phoneNumbersResponse )
        .addresses( addressesResponse )
        .build();
  }
}

