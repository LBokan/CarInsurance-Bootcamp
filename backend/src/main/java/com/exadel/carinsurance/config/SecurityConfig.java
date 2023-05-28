package com.exadel.carinsurance.config;

import com.exadel.carinsurance.config.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authProvider;

  @Autowired
  public SecurityConfig( JwtAuthFilter jwtAuthFilter,
                         AuthenticationProvider authProvider ) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authProvider = authProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests( request ->
            request
                .antMatchers( "/api/auth/**" ).permitAll()
                .anyRequest().authenticated()
        )
        .sessionManagement()
        .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
        .and()
        .authenticationProvider( authProvider )
        .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class );

    return http.build();
  }
}
