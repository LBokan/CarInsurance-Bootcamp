package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.VehicleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleInfoRepository extends JpaRepository<VehicleInfoEntity, Long> {
}
