package com.exadel.carinsurance.service;

import com.exadel.carinsurance.entity.UserEntity;
import com.exadel.carinsurance.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;

  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }
}
