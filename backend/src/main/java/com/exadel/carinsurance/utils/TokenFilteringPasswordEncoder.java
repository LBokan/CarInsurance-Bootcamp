package com.exadel.carinsurance.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TokenFilteringPasswordEncoder implements PasswordEncoder {
  private final PasswordEncoder passwordEncoder;

  public TokenFilteringPasswordEncoder( PasswordEncoder passwordEncoder ) {
    this.passwordEncoder = passwordEncoder;
  }

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
