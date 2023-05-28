package com.exadel.carinsurance.config.jwt;

import com.exadel.carinsurance.service.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
  private final UserDetailsService userDetailsService;

  @Autowired
  public JwtAuthFilter( IJwtService jwtService,
                        UserDetailsService userDetailsService ) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
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
    email = jwtService.extractEmail( jwt );

    if ( email != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername( email );
      boolean isTokenValid = jwtService.isTokenValid( jwt, userDetails );

      if ( isTokenValid ) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails( request )
        );

        SecurityContextHolder.getContext().setAuthentication( authToken );
      }
    }

    filterChain.doFilter( request, response );
  }
}

