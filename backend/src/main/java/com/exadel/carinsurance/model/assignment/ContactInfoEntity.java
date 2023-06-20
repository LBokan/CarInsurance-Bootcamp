package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
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

  @Column( name = "date_of_creation" )
  private LocalDateTime dateOfCreation;

  @Column( name = "type" )
  private String type;

  @Column( name = "first_name" )
  private String firstName;

  @Column( name = "last_name" )
  private String lastName;

  @Column( name = "email" )
  private String email;

  @Column( name = "assignment_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long assignmentId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @OneToMany( fetch = FetchType.EAGER,
      mappedBy = "contactInfo",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private List<PhoneNumberEntity> phoneNumbers;

  @OneToMany( fetch = FetchType.EAGER,
      mappedBy = "contactInfo",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private List<AddressEntity> addresses;

  @Override
  public String toString() {
    return "ContactInfoEntity{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumbers=" + phoneNumbers +
        ", addresses=" + addresses +
        '}';
  }
}
