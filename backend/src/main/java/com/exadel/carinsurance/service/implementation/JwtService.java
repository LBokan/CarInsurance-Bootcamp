package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService implements IJwtService {
  @Value( "${jwt.token.secret}" )
  private String secretKey;

  @Value( "${jwt.token.expirationMs}" )
  private int expirationMs;

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode( secretKey );

    return Keys.hmacShaKeyFor( keyBytes );
  }

  private Claims extractAllClaims( String token ) {
    return Jwts
        .parserBuilder()
        .setSigningKey( getSignInKey() )
        .build()
        .parseClaimsJws( token )
        .getBody();
  }

  @Override
  public String generateToken( UserDetails userDetails ) {
    return Jwts
        .builder()
        .setSubject( userDetails.getUsername() )
        .setIssuedAt( new Date( System.currentTimeMillis() ) )
        .setExpiration( new Date( System.currentTimeMillis() + expirationMs ) )
        .signWith( getSignInKey(), SignatureAlgorithm.HS256 )
        .compact();
  }

  @Override
  public boolean isTokenValid( String token, UserDetails userDetails ) {
    final String email = extractEmail( token );
    boolean isTokenExpired = extractAllClaims( token )
        .getExpiration()
        .before( new Date() );

    return ( email.equals( userDetails.getUsername() ) && !isTokenExpired );
  }

  @Override
  public String extractEmail( String token ) {
    return extractAllClaims( token ).getSubject();
  }
}

