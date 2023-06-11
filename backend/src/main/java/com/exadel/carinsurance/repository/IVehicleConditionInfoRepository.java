package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.VehicleConditionInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleConditionInfoRepository extends JpaRepository<VehicleConditionInfoEntity, Long> {
}
