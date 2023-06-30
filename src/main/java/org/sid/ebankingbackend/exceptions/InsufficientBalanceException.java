package org.sid.ebankingbackend.exceptions;

public class InsufficientBalanceException extends Exception {
  public InsufficientBalanceException(String className, int line) {
    super("Insufficient balance.");
  }
}
