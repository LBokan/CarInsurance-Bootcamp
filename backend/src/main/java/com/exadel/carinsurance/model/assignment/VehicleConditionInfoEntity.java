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
@Table( name = "vehicle_condition_info" )
public class VehicleConditionInfoEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @JsonIgnore
  @OneToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @ManyToMany( fetch = FetchType.LAZY,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @JoinTable(
      name = "directions_of_impact_vehicle_condition_info",
      joinColumns = @JoinColumn( name = "vehicle_condition_info_id" ),
      inverseJoinColumns = @JoinColumn( name = "direction_of_impact_id" )
  )
  private List<DirectionOfImpactEntity> directionsOfImpact;

  @Override
  public String toString() {
    return "VehicleConditionInfoEntity{" +
        "id=" + id +
        '}';
  }
}
