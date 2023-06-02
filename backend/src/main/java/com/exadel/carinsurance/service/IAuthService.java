package com.exadel.carinsurance.service;

import com.exadel.carinsurance.model.auth.AuthRequestEntity;
import com.exadel.carinsurance.model.auth.RegisterRequestEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthService {
  ResponseEntity signup( RegisterRequestEntity request );

  ResponseEntity login( AuthRequestEntity request,
                        HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse );
}
