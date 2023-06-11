package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<AddressEntity, Long> {
}
