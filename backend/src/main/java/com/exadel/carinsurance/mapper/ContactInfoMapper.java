package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import com.exadel.carinsurance.model.response.AddressResponseEntity;
import com.exadel.carinsurance.model.response.ContactInfoResponseEntity;
import com.exadel.carinsurance.model.response.PhoneNumberResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoMapper {
  public static ContactInfoEntity mapToContactInfo( ContactInfoRequestEntity contactInfoRequest ) {
    return ContactInfoEntity
        .builder()
        .type( contactInfoRequest.getType() )
        .firstName( contactInfoRequest.getFirstName() )
        .lastName( contactInfoRequest.getLastName() )
        .email( contactInfoRequest.getEmail() )
        .build();
  }

  public static ContactInfoResponseEntity mapToContactInfoResponse( ContactInfoEntity contactInfo ) {
    List<PhoneNumberResponseEntity> phoneNumbersResponse = new ArrayList<>();
    List<AddressResponseEntity> addressesResponse = new ArrayList<>();

    for ( PhoneNumberEntity phoneNumber : contactInfo.getPhoneNumbers() ) {
      PhoneNumberResponseEntity phoneNumberResponse = PhoneNumberMapper
          .mapToPhoneNumberResponse( phoneNumber );

      phoneNumbersResponse.add( phoneNumberResponse );
    }

    for ( AddressEntity address : contactInfo.getAddresses() ) {
      AddressResponseEntity addressResponse = AddressMapper
          .mapToAddressResponse( address );

      addressesResponse.add( addressResponse );
    }

    return ContactInfoResponseEntity
        .builder()
        .id( contactInfo.getId() )
        .type( contactInfo.getType() )
        .firstName( contactInfo.getFirstName() )
        .lastName( contactInfo.getLastName() )
        .email( contactInfo.getEmail() )
        .phoneNumbers( phoneNumbersResponse )
        .addresses( addressesResponse )
        .build();
  }
}

