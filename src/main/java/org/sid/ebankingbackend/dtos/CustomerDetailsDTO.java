package org.sid.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDetailsDTO extends CustomerDTO {
  private List<BankAccountDTO> currentAccounts = new ArrayList<>();
  private List<BankAccountDTO> savingAccounts = new ArrayList<>();
}
