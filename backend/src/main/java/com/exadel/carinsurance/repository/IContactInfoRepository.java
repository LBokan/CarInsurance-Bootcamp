package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IContactInfoRepository extends JpaRepository<ContactInfoEntity, Long> {
  Optional<ContactInfoEntity> findByDateOfCreation( LocalDateTime date );
}
