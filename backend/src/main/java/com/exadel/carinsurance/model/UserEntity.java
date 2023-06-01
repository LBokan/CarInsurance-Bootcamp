package com.exadel.carinsurance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "users" )
public class UserEntity implements UserDetails {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "user_id" )
  private Long userId;

  @Column( name = "first_name" )
  private String firstName;

  @Column( name = "last_name" )
  private String lastName;

  @Column( name = "email" )
  private String email;

  @Column( name = "password" )
  private String password;

  @Column( name = "role_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private String roleId;

  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "role_id" )
  private RoleEntity role;

  public UserEntity( String firstName, String lastName,
                     String email, String password ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of( new SimpleGrantedAuthority( role.getName().name() ) );
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
