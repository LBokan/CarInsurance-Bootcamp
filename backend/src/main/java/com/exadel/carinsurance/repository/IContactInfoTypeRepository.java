package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.ContactInfoTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IContactInfoTypeRepository extends JpaRepository<ContactInfoTypeEntity, Integer> {
  Optional<ContactInfoTypeEntity> findByName( String name );
}
