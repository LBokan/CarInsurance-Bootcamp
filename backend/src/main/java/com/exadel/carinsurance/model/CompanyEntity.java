package com.exadel.carinsurance.model;

import com.exadel.carinsurance.model.assignment.AssignmentEntity;
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
@Table( name = "companies" )
public class CompanyEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "name" )
  private String name;

  @Column( name = "type_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private int typeId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "type_id" )
  private CompanyTypeEntity type;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "company",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<UserEntity> users;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "insuranceAgency",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<AssignmentEntity> insuranceAgenciesAssignments;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "repairFacility",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<AssignmentEntity> repairFacilitiesAssignments;

  @Override
  public String toString() {
    return "CompanyEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", typeId='" + typeId + '\'' +
        '}';
  }
}
