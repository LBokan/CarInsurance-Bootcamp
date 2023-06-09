package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestEntity {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
