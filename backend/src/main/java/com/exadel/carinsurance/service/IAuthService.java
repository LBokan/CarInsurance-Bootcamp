package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.auth.AuthRequestEntity;
import com.exadel.carinsurance.model.auth.RegisterRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
  ResponseEntity signup( RegisterRequestEntity request );

  ResponseEntity login( AuthRequestEntity request );
}
