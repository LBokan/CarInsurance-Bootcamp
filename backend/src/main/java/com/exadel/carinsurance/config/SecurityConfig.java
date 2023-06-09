package com.exadel.carinsurance.config;

import com.exadel.carinsurance.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final AuthenticationProvider authProvider;

  @Autowired
  public SecurityConfig( AuthenticationProvider authProvider ) {
    this.authProvider = authProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthFilter jwtAuthFilter
  ) throws Exception {
    http
        .csrf()
        .disable()
        .cors()
        .configurationSource( corsConfigurationSource() )
        .and()
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

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin( "http://localhost:5173" );
    configuration.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "DELETE" ) );
    configuration.addAllowedHeader( "*" );
    configuration.setAllowCredentials( true );

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration( "/**", configuration );

    return source;
  }
}
