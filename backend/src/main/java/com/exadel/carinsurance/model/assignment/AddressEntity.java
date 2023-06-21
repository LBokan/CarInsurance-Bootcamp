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
@Table( name = "addresses" )
public class AddressEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "type" )
  private String type;

  @Column( name = "city" )
  private String city;

  @Column( name = "state" )
  private String state;

  @Column( name = "zip" )
  private String zip;

  @Column( name = "address_line" )
  private String addressLine;

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
    return "AddressEntity{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zip='" + zip + '\'' +
        ", addressLine='" + addressLine + '\'' +
        '}';
  }
}
