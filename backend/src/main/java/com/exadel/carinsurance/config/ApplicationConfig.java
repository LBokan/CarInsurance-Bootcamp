package com.exadel.carinsurance.config;

import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.utils.TokenFilteringPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  @Autowired
  public final IUserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail( username )
        .orElseThrow( () -> new UsernameNotFoundException( "The user is not found" ) );
  }

  @Bean
  public AuthenticationProvider authProvider(
      PasswordEncoder passwordEncoder
  ) {
    DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

    daoAuthProvider.setUserDetailsService( userDetailsService() );
    daoAuthProvider.setPasswordEncoder( passwordEncoder );

    return daoAuthProvider;
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Primary
  public PasswordEncoder customTokenFilteringPasswordEncoder() {
    return new TokenFilteringPasswordEncoder( bCryptPasswordEncoder() );
  }

  @Bean
  public AuthenticationManager authManager( AuthenticationConfiguration authConfig ) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
