package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.ContactInfoRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IContactInfoService {
  ContactInfoEntity createContactInfoInDB( AssignmentEntity assignment, ContactInfoRequestEntity request );

  ResponseEntity createContactInfo( ContactInfoRequestEntity request );

  ResponseEntity editContactInfo( Long contactInfoId, ContactInfoRequestEntity request );

  ResponseEntity deleteContactInfo( Long contactInfoId );

}
