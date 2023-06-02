package com.exadel.carinsurance.config;

import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.utils.TokenFilteringPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  @Autowired
  public final IUserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail( username )
        .orElseThrow( () -> new UsernameNotFoundException( "User not found" ) );
  }

  @Bean
  public AuthenticationProvider authProvider() {
    DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

    daoAuthProvider.setUserDetailsService( userDetailsService() );
    daoAuthProvider.setPasswordEncoder( tokenFilteringPasswordEncoder() );

    return daoAuthProvider;
  }

  @Bean
  public PasswordEncoder tokenFilteringPasswordEncoder() {
    return new TokenFilteringPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authManager( AuthenticationConfiguration authConfig ) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
