package com.exadel.carinsurance.repository;

import com.exadel.carinsurance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
