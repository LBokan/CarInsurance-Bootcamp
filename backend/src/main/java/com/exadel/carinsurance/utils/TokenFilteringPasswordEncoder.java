package com.exadel.carinsurance.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TokenFilteringPasswordEncoder implements PasswordEncoder {
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public String encode( CharSequence rawPassword ) {
    return passwordEncoder.encode( rawPassword );
  }

  @Override
  public boolean matches( CharSequence rawPassword,
                          String encodedPassword ) {
    if ( "".contentEquals( rawPassword ) ) {
      return true;
    } else {
      return passwordEncoder
          .matches( rawPassword, encodedPassword );
    }
  }
}
