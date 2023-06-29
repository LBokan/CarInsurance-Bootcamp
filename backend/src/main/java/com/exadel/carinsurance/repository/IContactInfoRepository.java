package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactInfoRepository extends JpaRepository<ContactInfoEntity, Long> {
}
