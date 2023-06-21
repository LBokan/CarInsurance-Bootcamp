package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.AlreadyExistsException;
import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toResponse.UserResponseMapper;
import com.exadel.carinsurance.model.ERoleEntity;
import com.exadel.carinsurance.model.RoleEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.exadel.carinsurance.model.request.AuthRequestEntity;
import com.exadel.carinsurance.model.request.RegisterRequestEntity;
import com.exadel.carinsurance.model.response.UserResponseEntity;
import com.exadel.carinsurance.repository.IRoleRepository;
import com.exadel.carinsurance.repository.IUserRepository;
import com.exadel.carinsurance.service.IAuthService;
import com.exadel.carinsurance.service.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
  private final IRoleRepository roleRepository;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final IJwtService jwtService;
  private final AuthenticationManager authManager;
  private final UserResponseMapper userResponseMapper;

  @Autowired
  public AuthService( IRoleRepository roleRepository,
                      IUserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      IJwtService jwtService,
                      AuthenticationManager authManager,
                      UserResponseMapper userResponseMapper
  ) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.userResponseMapper = userResponseMapper;
  }

  @Override
  public ResponseEntity signup( RegisterRequestEntity request ) {
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
  public ResponseEntity login( AuthRequestEntity request ) {
    String userEmail = request.getEmail();

    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userEmail,
            request.getPassword()
        )
    );

    UserEntity userEntity = ( UserEntity ) authentication.getPrincipal();
    UserResponseEntity userResponse = userResponseMapper.toResponse( userEntity );
    String jwtToken = jwtService.generateToken( userEmail );

    ResponseCookie cookie = ResponseCookie.from( "token", jwtToken )
        .path( "/" )
        .maxAge( 86400 )
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.add( HttpHeaders.SET_COOKIE, cookie.toString() );

    return ResponseEntity
        .ok()
        .headers( headers )
        .body( userResponse );
  }
}

