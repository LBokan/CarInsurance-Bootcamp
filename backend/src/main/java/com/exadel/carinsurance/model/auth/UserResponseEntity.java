package com.exadel.carinsurance.model.auth;

import com.exadel.carinsurance.model.RoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseEntity {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private RoleEntity role;

  public static UserResponseEntity fromUserEntity( UserEntity userEntity ) {
    return UserResponseEntity.builder()
        .userId( userEntity.getUserId() )
        .firstName( userEntity.getFirstName() )
        .lastName( userEntity.getLastName() )
        .email( userEntity.getEmail() )
        .role( userEntity.getRole() )
        .build();
  }
}
