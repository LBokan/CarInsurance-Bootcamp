package com.exadel.carinsurance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "roles" )
public class RoleEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private int id;

  @Enumerated( EnumType.STRING )
  @Column( name = "name" )
  private ERoleEntity name;

  @JsonIgnore
  @OneToMany( fetch = FetchType.EAGER,
      mappedBy = "role",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private Set<UserEntity> users;

  public RoleEntity( ERoleEntity name ) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public ERoleEntity getName() {
    return name;
  }

  public void setName( ERoleEntity name ) {
    this.name = name;
  }

  public Set<UserEntity> getUsers() {
    return users;
  }

  public void setUsers( Set<UserEntity> users ) {
    this.users = users;
  }
}
