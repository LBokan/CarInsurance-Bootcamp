package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;

import java.util.List;

public interface IUserService {

  UserResponseEntity getUser();

  List<UserEntity> findAll();
}
