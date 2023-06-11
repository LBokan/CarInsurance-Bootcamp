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
@Table( name = "directions_of_impact" )
public class DirectionOfImpactEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "name" )
  private String name;

  @JsonIgnore
  @ManyToMany( fetch = FetchType.LAZY,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @JoinTable(
      name = "directions_of_impact_vehicle_condition_info",
      joinColumns = @JoinColumn( name = "direction_of_impact_id" ),
      inverseJoinColumns = @JoinColumn( name = "vehicle_condition_info_id" )
  )
  private List<VehicleConditionInfoEntity> vehicleConditionInfo;

  @Override
  public String toString() {
    return "DirectionsOfImpactEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
