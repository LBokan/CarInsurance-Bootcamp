package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.model.CompanyEntity;
import com.exadel.carinsurance.model.CompanyTypeEntity;
import com.exadel.carinsurance.model.ECompanyTypeEntity;
import com.exadel.carinsurance.repository.ICompanyRepository;
import com.exadel.carinsurance.repository.ICompanyTypeRepository;
import com.exadel.carinsurance.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyService implements ICompanyService {
  private final ICompanyTypeRepository companyTypeRepository;
  private final ICompanyRepository companyRepository;

  @Autowired
  public CompanyService(
      ICompanyTypeRepository companyTypeRepository,
      ICompanyRepository companyRepository
  ) {
    this.companyTypeRepository = companyTypeRepository;
    this.companyRepository = companyRepository;
  }

  @Override
  public ResponseEntity getInsuranceAgencies() {
    CompanyTypeEntity companyTypeFromDB = companyTypeRepository
        .findByName( ECompanyTypeEntity.INSURANCE_AGENCY )
        .orElseThrow( () ->
            new NotFoundException( "The company type is not found" )
        );

    List<CompanyEntity> companiesFromDB = companyRepository
        .findAllByTypeId( companyTypeFromDB.getId() )
        .orElseThrow( () ->
            new NotFoundException( "Companies are not found" )
        );

    List<String> companiesResponse = new ArrayList<>();

    for ( CompanyEntity company : companiesFromDB ) {
      companiesResponse.add( company.getName() );
    }

    return ResponseEntity
        .ok()
        .body( companiesResponse );
  }

  @Override
  public ResponseEntity getRepairFacilities() {
    CompanyTypeEntity companyTypeFromDB = companyTypeRepository
        .findByName( ECompanyTypeEntity.REPAIR_FACILITY )
        .orElseThrow( () ->
            new NotFoundException( "The company type is not found" )
        );

    List<CompanyEntity> companiesFromDB = companyRepository
        .findAllByTypeId( companyTypeFromDB.getId() )
        .orElseThrow( () ->
            new NotFoundException( "Companies are not found" )
        );

    List<String> companiesResponse = new ArrayList<>();

    for ( CompanyEntity company : companiesFromDB ) {
      companiesResponse.add( company.getName() );
    }

    return ResponseEntity
        .ok()
        .body( companiesResponse );
  }
}
