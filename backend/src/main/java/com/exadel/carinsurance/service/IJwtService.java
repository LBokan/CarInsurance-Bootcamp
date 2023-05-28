package com.exadel.carinsurance.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
  String generateToken( UserDetails userDetails );

  String extractEmail( String token );

  boolean isTokenValid( String token, UserDetails userDetails );
}
