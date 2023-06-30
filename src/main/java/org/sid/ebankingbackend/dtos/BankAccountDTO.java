package org.sid.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.enums.AccountStatus;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public abstract class BankAccountDTO {
  private String id;
  private String type;
  private double balance;
  private Date createdAt;
  private AccountStatus status;
  private CustomerDTO customer;
}
