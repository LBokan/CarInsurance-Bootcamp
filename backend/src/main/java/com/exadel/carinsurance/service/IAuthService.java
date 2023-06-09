package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.request.AuthRequestEntity;
import com.exadel.carinsurance.model.request.RegisterRequestEntity;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
  ResponseEntity signup( RegisterRequestEntity request );

  ResponseEntity login( AuthRequestEntity request );
}
