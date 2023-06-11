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

  @Column( name = "date_of_creation" )
  private LocalDateTime dateOfCreation;

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
      List<ContactInfoEntity> contactsInfo,
      VehicleInfoEntity vehicleInfo,
      VehicleConditionInfoEntity vehicleConditionInfo
  ) {
    this.contactsInfo = contactsInfo;
    this.vehicleInfo = vehicleInfo;
    this.vehicleConditionInfo = vehicleConditionInfo;
  }

  @Override
  public String toString() {
    return "AssignmentEntity{" +
        "assignmentId=" + assignmentId +
        ", contactsInfo=" + contactsInfo +
        ", vehicleInfo=" + vehicleInfo +
        ", vehicleConditionInfo=" + vehicleConditionInfo +
        '}';
  }
}
