package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.CompanyTypeEntity;
import com.exadel.carinsurance.model.ECompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICompanyTypeRepository extends JpaRepository<CompanyTypeEntity, Integer> {
  Optional<CompanyTypeEntity> findByName( ECompanyTypeEntity name );
}
