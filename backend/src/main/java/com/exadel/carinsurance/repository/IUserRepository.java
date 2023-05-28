package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail( String email );

  Boolean existsByEmail( String email );
}
