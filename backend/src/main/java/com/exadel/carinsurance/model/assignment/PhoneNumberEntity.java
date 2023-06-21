package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "phone_numbers" )
public class PhoneNumberEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "type" )
  private String type;

  @Column( name = "number" )
  private String number;

  @Column( name = "contacts_info_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long contactInfoId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "contacts_info_id" )
  private ContactInfoEntity contactInfo;

  @Override
  public String toString() {
    return "PhoneNumberEntity{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", number='" + number + '\'' +
        '}';
  }
}
