package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "contacts_info" )
public class ContactInfoEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "first_name" )
  private String firstName;

  @Column( name = "last_name" )
  private String lastName;

  @Column( name = "email" )
  private String email;

  @Column( name = "type_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private int typeId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "type_id" )
  private ContactInfoTypeEntity type;

  @Column( name = "assignment_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long assignmentId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "contactInfo",
      cascade = CascadeType.ALL,
      orphanRemoval = true )
  private List<PhoneNumberEntity> phoneNumbers;

  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "contactInfo",
      cascade = CascadeType.ALL,
      orphanRemoval = true )
  private List<AddressEntity> addresses;

  @Override
  public String toString() {
    return "ContactInfoEntity{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", typeId=" + typeId +
        ", assignmentId=" + assignmentId +
        '}';
  }
}
