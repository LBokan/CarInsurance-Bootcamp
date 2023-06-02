package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName( ERoleEntity name );
}
