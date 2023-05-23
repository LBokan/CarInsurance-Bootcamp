package com.exadel.carinsurance.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_id")
  private Long userId;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  @Column(name="email")
  private String email;

  @Column(name="password")
  private String password;

  @Column(name="role_id")
  private String roleId;

  public UserEntity() {
  }

  public UserEntity( String firstName, String lastName, String email, String password ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId( Long userId ) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId( String roleId ) {
    this.roleId = roleId;
  }
}