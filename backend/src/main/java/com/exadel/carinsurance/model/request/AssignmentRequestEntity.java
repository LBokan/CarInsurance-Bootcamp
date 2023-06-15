package com.exadel.carinsurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequestEntity {
  private Date dateOfIncident;
  private List<ContactInfoRequestEntity> contactsInfo;
  private VehicleInfoRequestEntity vehicleInfo;
  private VehicleConditionInfoRequestEntity vehicleConditionInfo;
}
