package com.exadel.carinsurance.exceptions;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException( String message ) {
    super( message );
  }
}
