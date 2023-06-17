package com.exadel.carinsurance.model.assignment;

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
  @Column( name = "assignment_id" )
  private Long assignmentId;

  @Column( name = "user_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private String userId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "user_id" )
  private UserEntity user;

  @Column( name = "status_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private String statusId;
  
  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "status_id" )
  private AssignmentStatusEntity status;

  @Column( name = "date_of_creation" )
  private LocalDateTime dateOfCreation;

  @Column( name = "date_of_incident" )
  private Date dateOfIncident;

  @OneToMany( fetch = FetchType.EAGER,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private List<ContactInfoEntity> contactsInfo;

  @OneToOne( fetch = FetchType.EAGER,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private VehicleInfoEntity vehicleInfo;

  @OneToOne( fetch = FetchType.EAGER,
      mappedBy = "assignment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private VehicleConditionInfoEntity vehicleConditionInfo;

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
        "assignmentId=" + assignmentId +
        ", dateOfIncident=" + dateOfIncident +
        ", contactsInfo=" + contactsInfo +
        ", vehicleInfo=" + vehicleInfo +
        ", vehicleConditionInfo=" + vehicleConditionInfo +
        '}';
  }
}
