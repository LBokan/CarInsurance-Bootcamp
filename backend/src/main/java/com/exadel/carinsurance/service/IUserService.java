package com.exadel.carinsurance.service;

import com.exadel.carinsurance.entity.UserEntity;

import java.util.List;

public interface IUserService {
  List<UserEntity> findAll();
}
