package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.AlreadyExistsException;
import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.RoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.auth.AuthRequestEntity;
import com.exadel.carinsurance.model.auth.AuthResponseEntity;
import com.exadel.carinsurance.model.auth.RegisterRequestEntity;
import com.exadel.carinsurance.repository.IRoleRepository;
import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.service.IAuthService;
import com.exadel.carinsurance.service.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService implements IAuthService {
  private final IRoleRepository roleRepository;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final IJwtService jwtService;
  private final AuthenticationManager authManager;

  @Autowired
  public AuthService( IRoleRepository roleRepository,
                      IUserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      IJwtService jwtService,
                      AuthenticationManager authManager ) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authManager = authManager;
  }

  @Override
  public ResponseEntity signup( @RequestBody RegisterRequestEntity request ) {
    String userEmail = request.getEmail();
    ERoleEntity userRole = ERoleEntity.ROLE_CLIENT;

    if ( userRepository.existsByEmail( userEmail ) ) {
      throw new AlreadyExistsException(
          String.format( "A user with the email address: %s is already exists", userEmail )
      );
    }

    RoleEntity roleFromDB = roleRepository
        .findByName( userRole )
        .orElseThrow( () ->
            new NotFoundException( "The role is not found" )
        );

    UserEntity user = UserEntity
        .builder()
        .firstName( request.getFirstName() )
        .lastName( request.getLastName() )
        .email( userEmail )
        .password( passwordEncoder.encode( request.getPassword() ) )
        .role( roleFromDB )
        .build();

    userRepository.save( user );

    return ResponseEntity
        .ok( "User successfully created" );
  }

  @Override
  public ResponseEntity login( @RequestBody AuthRequestEntity request,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse ) {
    String userEmail = request.getEmail();

    Cookie[] cookies = httpServletRequest.getCookies();
    if ( cookies != null ) {
      for ( Cookie cookie : cookies ) {
        cookie.setMaxAge( 0 );
        cookie.setPath( "/" );

        httpServletResponse.addCookie( cookie );
      }
    }

    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userEmail,
            request.getPassword()
        )
    );

    UserEntity user = ( UserEntity ) authentication.getPrincipal();
    String jwtToken = jwtService.generateToken( userEmail );

    Cookie cookie = new Cookie( "token", jwtToken );
    cookie.setMaxAge( 86400 );
    cookie.setPath( "/" );

    httpServletResponse.addCookie( cookie );
    httpServletResponse.setHeader( HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie" );

    AuthResponseEntity response = AuthResponseEntity
        .builder()
        .user( user )
        .token( jwtToken )
        .build();

    return ResponseEntity.ok( response );
  }
}

