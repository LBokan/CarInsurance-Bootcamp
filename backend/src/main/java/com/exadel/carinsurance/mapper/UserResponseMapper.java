package com.exadel.carinsurance.mapper;

import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;

public class UserResponseMapper {
  public static UserResponseEntity mapToUserResponse( UserEntity userEntity ) {
    return UserResponseEntity.builder()
        .userId( userEntity.getUserId() )
        .firstName( userEntity.getFirstName() )
        .lastName( userEntity.getLastName() )
        .email( userEntity.getEmail() )
        .role( userEntity.getRole() )
        .build();
  }
}
