package com.exadel.carinsurance.model.auth;

import com.exadel.carinsurance.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseEntity {
  private UserEntity user;
  private String token;
}
