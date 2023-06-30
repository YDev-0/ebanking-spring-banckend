package org.sid.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {
  public BankAccountNotFoundException() {
    super("Account not found.");
  }
}
