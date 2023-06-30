package org.sid.ebankingbackend.exceptions;

public class IllegalOperationTypeException extends Exception {
  public IllegalOperationTypeException(String type) {
    super("No enum constant OperationType." + type);
  }
}
