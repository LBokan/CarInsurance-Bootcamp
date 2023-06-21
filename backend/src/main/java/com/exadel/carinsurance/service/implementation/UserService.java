package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.mapper.toResponse.UserResponseMapper;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;
import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
  private final IUserRepository userRepository;
  private final UserResponseMapper userResponseMapper;

  @Autowired
  public UserService(
      IUserRepository userRepository,
      UserResponseMapper userResponseMapper
  ) {
    this.userRepository = userRepository;
    this.userResponseMapper = userResponseMapper;
  }

  @Override
  public UserResponseEntity getUser() {
    UserEntity user = ( UserEntity ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return userResponseMapper.toResponse( user );
  }

  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }
}
