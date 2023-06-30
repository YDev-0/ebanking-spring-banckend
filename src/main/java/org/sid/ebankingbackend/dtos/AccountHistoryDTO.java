package org.sid.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountHistoryDTO {
  private String type;
  private double balance;
  private AccountStatus status;
  private int currentPage;
  private int totalPages;
  private int pageSize;
  private List<AccountOperationDTO> accountOperations;
}
