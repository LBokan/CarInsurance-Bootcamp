package com.exadel.carinsurance.model.assignment;

public enum EAssignmentStatusEntity {
  NEW( "New" ),
  IN_PROGRESS( "In progress" ),
  ASSIGNED( "Assigned" ),
  IN_REVIEW( "In review" ),
  TOTAL_LOSS( "Total loss" ),
  REPAIR( "Repair" ),
  REPAIRED( "Repaired" );

  private final String label;

  EAssignmentStatusEntity( String label ) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
