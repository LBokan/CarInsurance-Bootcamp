package com.exadel.carinsurance.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException( String message ) {
    super( message );
  }
}
