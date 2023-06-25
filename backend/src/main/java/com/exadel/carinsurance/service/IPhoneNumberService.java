package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import com.exadel.carinsurance.model.request.PhoneNumberRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IPhoneNumberService {
  PhoneNumberEntity createPhoneNumberInDB( ContactInfoEntity contactInfo, PhoneNumberRequestEntity request );

  ResponseEntity createPhoneNumber( PhoneNumberRequestEntity request );

  ResponseEntity editPhoneNumber( Long phoneNumberId, PhoneNumberRequestEntity request );

  ResponseEntity deletePhoneNumber( Long phoneNumberId );

}
