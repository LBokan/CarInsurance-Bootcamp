package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.PhoneNumberTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPhoneNumberTypeRepository extends JpaRepository<PhoneNumberTypeEntity, Integer> {
  Optional<PhoneNumberTypeEntity> findByName( String name );
}
