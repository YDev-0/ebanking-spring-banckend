package org.sid.ebankingbackend.exceptions;

public class PageNotFoundException extends Exception {
  public PageNotFoundException() {
    super("Page not found.");
  }
}
