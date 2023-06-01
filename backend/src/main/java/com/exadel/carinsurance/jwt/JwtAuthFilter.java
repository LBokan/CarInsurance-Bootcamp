package com.exadel.carinsurance.jwt;

import com.exadel.carinsurance.service.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final IJwtService jwtService;
  private final AuthenticationManager authManager;

  @Autowired
  public JwtAuthFilter( IJwtService jwtService,
                        AuthenticationManager authManager ) {
    this.jwtService = jwtService;
    this.authManager = authManager;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader( "Authorization" );
    final String jwt;
    final String email;

    if ( authHeader == null || !authHeader.startsWith( "Bearer " ) ) {
      filterChain.doFilter( request, response );

      return;
    }

    jwt = authHeader.substring( 7 );

    try {
      email = jwtService.extractEmail( jwt );
    } catch ( ExpiredJwtException ex ) {
      response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
      response.getWriter().write( "Token is expired" );

      return;
    }

    if ( email != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken( email, "password" );
      Authentication authentication = authManager.authenticate( authToken );

      SecurityContextHolder.getContext().setAuthentication( authentication );
    }

    filterChain.doFilter( request, response );
  }
}

