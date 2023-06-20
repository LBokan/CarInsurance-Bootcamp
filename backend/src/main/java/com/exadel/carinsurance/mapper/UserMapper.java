package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<UserEntity, UserEntity, UserResponseEntity> {
  @Override
  public UserEntity toEntity( UserEntity request ) {
    return null;
  }

  @Override
  public UserResponseEntity toResponse( UserEntity entity ) {
    return UserResponseEntity.builder()
        .userId( entity.getUserId() )
        .firstName( entity.getFirstName() )
        .lastName( entity.getLastName() )
        .email( entity.getEmail() )
        .role( entity.getRole() )
        .build();
  }
}
