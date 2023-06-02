package com.exadel.carinsurance.controller;

import com.exadel.carinsurance.model.auth.AuthRequestEntity;
import com.exadel.carinsurance.model.auth.RegisterRequestEntity;
import com.exadel.carinsurance.service.implementation.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping( "/api/auth" )
@RequiredArgsConstructor
public class AuthController {
  @Autowired
  private final AuthService authService;

  @PostMapping( "/signup" )
  public ResponseEntity signup(
      @RequestBody RegisterRequestEntity request
  ) {
    return authService.signup( request );
  }

  @PostMapping( "/login" )
  public ResponseEntity login(
      @RequestBody AuthRequestEntity request,
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse
  ) {
    return authService.login( request, httpServletRequest, httpServletResponse );
  }
}
