package com.exadel.carinsurance.service;

import org.springframework.http.ResponseEntity;

public interface ICompanyService {
  ResponseEntity getInsuranceAgencies();

  ResponseEntity getRepairFacilities();
}
