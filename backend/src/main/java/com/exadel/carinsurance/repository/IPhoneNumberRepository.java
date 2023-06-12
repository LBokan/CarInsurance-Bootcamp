package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
}
