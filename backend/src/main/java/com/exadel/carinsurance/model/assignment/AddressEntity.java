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

  @Column( name = "city" )
  private String city;

  @Column( name = "state" )
  private String state;

  @Column( name = "zip" )
  private String zip;

  @Column( name = "address_line" )
  private String addressLine;

  @Column( name = "type_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private int typeId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "type_id" )
  private AddressTypeEntity type;

  @Column( name = "contact_info_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long contactInfoId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "contact_info_id" )
  private ContactInfoEntity contactInfo;

  @Override
  public String toString() {
    return "AddressEntity{" +
        "id=" + id +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zip='" + zip + '\'' +
        ", addressLine='" + addressLine + '\'' +
        ", typeId=" + typeId +
        ", contactInfoId=" + contactInfoId +
        '}';
  }
}
