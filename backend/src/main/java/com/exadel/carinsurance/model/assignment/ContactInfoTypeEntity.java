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
@Table( name = "contact_info_types" )
public class ContactInfoTypeEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private int id;

  @Column( name = "name" )
  private String name;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "type",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<ContactInfoEntity> contacts;

  @Override
  public String toString() {
    return "ContactInfoTypeEntity{" +
        "id=" + id +
        ", name=" + name +
        '}';
  }
}
