package com.exadel.carinsurance.model.assignment;

import com.exadel.carinsurance.model.CompanyEntity;
import com.exadel.carinsurance.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "assignments" )
public class AssignmentEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "date_of_creation" )
  private LocalDateTime dateOfCreation;

  @Column( name = "date_of_incident" )
  private Date dateOfIncident;

  @Column( name = "status_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private int statusId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "status_id" )
  private AssignmentStatusEntity status;

  @Column( name = "insurance_agency_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long insuranceAgencyId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "insurance_agency_id" )
  private CompanyEntity insuranceAgency;

  @Column( name = "repair_facility_id",
      insertable = false,
      updatable = false )
  private Long repairFacilityId;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "repair_facility_id" )
  private CompanyEntity repairFacility;

  @Column( name = "user_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long userId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "user_id" )
  private UserEntity user;

  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "assignment",
      cascade = CascadeType.ALL,
      orphanRemoval = true )
  private List<ContactInfoEntity> contactsInfo;

  @OneToOne( fetch = FetchType.LAZY,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private VehicleInfoEntity vehicleInfo;

  @OneToOne( fetch = FetchType.LAZY,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private VehicleConditionInfoEntity vehicleConditionInfo;

  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private List<CommentEntity> comments;

  public AssignmentEntity(
      Date dateOfIncident,
      List<ContactInfoEntity> contactsInfo,
      VehicleInfoEntity vehicleInfo,
      VehicleConditionInfoEntity vehicleConditionInfo
  ) {
    this.dateOfIncident = dateOfIncident;
    this.contactsInfo = contactsInfo;
    this.vehicleInfo = vehicleInfo;
    this.vehicleConditionInfo = vehicleConditionInfo;
  }

  @Override
  public String toString() {
    return "AssignmentEntity{" +
        "id=" + id +
        ", dateOfCreation=" + dateOfCreation +
        ", dateOfIncident=" + dateOfIncident +
        ", statusId=" + statusId +
        ", insuranceAgencyId=" + insuranceAgencyId +
        ", repairFacilityId=" + repairFacilityId +
        ", userId=" + userId +
        '}';
  }
}
