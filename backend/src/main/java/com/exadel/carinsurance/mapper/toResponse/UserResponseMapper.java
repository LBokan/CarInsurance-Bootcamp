package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements IResponseMapper<UserEntity, UserResponseEntity> {
  @Override
  public UserResponseEntity toResponse( UserEntity entity ) {
    UserResponseEntity userResponse = UserResponseEntity
        .builder()
        .id( entity.getId() )
        .firstName( entity.getFirstName() )
        .lastName( entity.getLastName() )
        .email( entity.getEmail() )
        .role( entity.getRole().getName().name() )
        .build();

    if (entity.getRole().getName().equals( ERoleEntity.ROLE_CLIENT )) {
      userResponse.setInsuranceCompany( entity.getCompany().getName() );
    } else {
      userResponse.setCompanyOfWork( entity.getCompany().getName() );
    }

    return userResponse;
  }
}
