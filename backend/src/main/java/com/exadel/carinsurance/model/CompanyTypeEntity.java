package com.exadel.carinsurance.model;

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
@Table( name = "company_types" )
public class CompanyTypeEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private int id;

  @Enumerated( EnumType.STRING )
  @Column( name = "name" )
  private ECompanyTypeEntity name;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "type",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<CompanyEntity> companies;

  @Override
  public String toString() {
    return "CompanyTypeEntity{" +
        "id=" + id +
        ", name=" + name +
        '}';
  }
}
