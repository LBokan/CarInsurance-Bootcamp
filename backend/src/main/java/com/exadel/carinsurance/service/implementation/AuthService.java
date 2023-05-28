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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
  public ResponseEntity login( @RequestBody AuthRequestEntity request ) {
    String userEmail = request.getEmail();

    UserEntity user = userRepository.findByEmail( userEmail )
        .orElseThrow( () ->
            new UsernameNotFoundException(
                "A user with the email address does not exist"
            ) );

    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userEmail,
            request.getPassword()
        )
    );

    String jwtToken = jwtService.generateToken( user );

    AuthResponseEntity response = AuthResponseEntity
        .builder()
        .user( user )
        .token( jwtToken )
        .build();

    return ResponseEntity.ok( response );
  }
}

