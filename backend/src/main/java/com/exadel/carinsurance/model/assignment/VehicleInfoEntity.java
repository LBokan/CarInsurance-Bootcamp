package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "vehicle_info" )
public class VehicleInfoEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "vin_number" )
  private String vinNumber;

  @Column( name = "car_make" )
  private String carMake;

  @Column( name = "car_model" )
  private String carModel;

  @Column( name = "year_of_manufacture" )
  private int yearOfManufacture;

  @Column( name = "odometer_value" )
  private String odometerValue;

  @Column( name = "license_plate_number" )
  private String licensePlateNumber;

  @Column( name = "license_state" )
  private String licenseState;

  @Column( name = "license_expiration_date" )
  private Date licenseExpirationDate;

  @Column( name = "assignment_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private String assignmentId;

  @JsonIgnore
  @OneToOne( fetch = FetchType.EAGER,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH },
      optional = false )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @Override
  public String toString() {
    return "VehicleInfoEntity{" +
        "id=" + id +
        ", vinNumber='" + vinNumber + '\'' +
        ", carMake='" + carMake + '\'' +
        ", carModel='" + carModel + '\'' +
        ", yearOfManufacture='" + yearOfManufacture + '\'' +
        ", odometerValue='" + odometerValue + '\'' +
        ", licensePlateNumber='" + licensePlateNumber + '\'' +
        ", licenseState='" + licenseState + '\'' +
        ", licenseExpirationDate='" + licenseExpirationDate + '\'' +
        '}';
  }
}
