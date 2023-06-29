package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.model.assignment.AddressTypeEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoTypeEntity;
import com.exadel.carinsurance.model.assignment.PhoneNumberTypeEntity;
import com.exadel.carinsurance.repository.IAddressTypeRepository;
import com.exadel.carinsurance.repository.IContactInfoTypeRepository;
import com.exadel.carinsurance.repository.IPhoneNumberTypeRepository;
import com.exadel.carinsurance.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TypeService implements ITypeService {
  private final IContactInfoTypeRepository contactInfoTypeRepository;
  private final IPhoneNumberTypeRepository phoneNumberTypeRepository;
  private final IAddressTypeRepository addressTypeRepository;

  @Autowired
  public TypeService(
      IContactInfoTypeRepository contactInfoTypeRepository,
      IPhoneNumberTypeRepository phoneNumberTypeRepository,
      IAddressTypeRepository addressTypeRepository
  ) {
    this.contactInfoTypeRepository = contactInfoTypeRepository;
    this.phoneNumberTypeRepository = phoneNumberTypeRepository;
    this.addressTypeRepository = addressTypeRepository;
  }

  @Override
  public ResponseEntity getContactInfoTypes() {
    List<ContactInfoTypeEntity> contactInfoTypes = contactInfoTypeRepository.findAll();

    List<String> contactInfoTypesResponse = new ArrayList<>();

    for ( ContactInfoTypeEntity contactInfoType : contactInfoTypes ) {
      contactInfoTypesResponse.add( contactInfoType.getName() );
    }

    return ResponseEntity
        .ok()
        .body( contactInfoTypesResponse );
  }

  @Override
  public ResponseEntity getPhoneNumberTypes() {
    List<PhoneNumberTypeEntity> phoneNumberTypes = phoneNumberTypeRepository.findAll();

    List<String> phoneNumberTypesResponse = new ArrayList<>();

    for ( PhoneNumberTypeEntity phoneNumberType : phoneNumberTypes ) {
      phoneNumberTypesResponse.add( phoneNumberType.getName() );
    }

    return ResponseEntity
        .ok()
        .body( phoneNumberTypesResponse );
  }

  @Override
  public ResponseEntity getAddressTypes() {
    List<AddressTypeEntity> addressTypes = addressTypeRepository.findAll();

    List<String> addressTypesResponse = new ArrayList<>();

    for ( AddressTypeEntity addressType : addressTypes ) {
      addressTypesResponse.add( addressType.getName() );
    }

    return ResponseEntity
        .ok()
        .body( addressTypesResponse );
  }
}
