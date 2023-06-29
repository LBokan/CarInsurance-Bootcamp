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

  @Column( name = "number" )
  private String number;

  @Column( name = "type_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private int typeId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "type_id" )
  private PhoneNumberTypeEntity type;

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
    return "PhoneNumberEntity{" +
        "id=" + id +
        ", number='" + number + '\'' +
        ", typeId=" + typeId +
        ", contactInfoId=" + contactInfoId +
        '}';
  }
}
