package com.exadel.carinsurance.model.response;

import com.exadel.carinsurance.model.RoleEntity;
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
}
