package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAddressTypeRepository extends JpaRepository<AddressTypeEntity, Integer> {
  Optional<AddressTypeEntity> findByName( String name );
}
