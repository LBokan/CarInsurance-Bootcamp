package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.service.IUserService;
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
