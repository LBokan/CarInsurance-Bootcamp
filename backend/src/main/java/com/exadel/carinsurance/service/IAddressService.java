package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IAddressService {
  AddressEntity createAddressInDB( ContactInfoEntity contactInfo, AddressRequestEntity request );

  ResponseEntity createAddress( AddressRequestEntity request );

  ResponseEntity editAddress( Long addressId, AddressRequestEntity request );

  ResponseEntity deleteAddress( Long addressId );

}
