package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long> {
  Optional<List<CompanyEntity>> findAllByTypeId( int typeId );

  Optional<CompanyEntity> findByName( String name );
}
