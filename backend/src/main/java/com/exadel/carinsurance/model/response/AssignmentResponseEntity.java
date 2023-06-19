package com.exadel.carinsurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseEntity {
  private Long assignmentId;
  private String status;
  private LocalDateTime dateOfCreation;
  private Date dateOfIncident;
  private List<ContactInfoResponseEntity> contactsInfo;
  private VehicleInfoResponseEntity vehicleInfo;
  private VehicleConditionInfoResponseEntity vehicleConditionInfo;
}
