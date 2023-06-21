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
@Table( name = "vehicle_condition_info" )
public class VehicleConditionInfoEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "names_of_photos_of_impact" )
  private String namesOfPhotosOfImpact;

  @Column( name = "direction_of_impact_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private String directionOfImpactId;

  @ManyToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "direction_of_impact_id" )
  private DirectionOfImpactEntity directionOfImpact;

  @Column( name = "assignment_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long assignmentId;

  @JsonIgnore
  @OneToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @Override
  public String toString() {
    return "VehicleConditionInfoEntity{" +
        "id=" + id +
        ", namesOfPhotosOfImpact='" + namesOfPhotosOfImpact + '\'' +
        ", directionOfImpact=" + directionOfImpact +
        '}';
  }
}
