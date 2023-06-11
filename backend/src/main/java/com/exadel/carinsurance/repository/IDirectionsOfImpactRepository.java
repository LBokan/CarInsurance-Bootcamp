package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.assignment.DirectionOfImpactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDirectionsOfImpactRepository extends JpaRepository<DirectionOfImpactEntity, Long> {
  Optional<DirectionOfImpactEntity> findByName( String name );
}
