package com.exadel.carinsurance.service;

import org.springframework.http.ResponseEntity;

public interface ITypeService {
  ResponseEntity getContactInfoTypes();

  ResponseEntity getPhoneNumberTypes();

  ResponseEntity getAddressTypes();
}
