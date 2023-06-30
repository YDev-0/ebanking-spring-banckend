package org.sid.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.TransferDTO;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.IllegalOperationTypeException;
import org.sid.ebankingbackend.exceptions.InsufficientBalanceException;
import org.sid.ebankingbackend.services.AccountOperationsService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("http://localhost:4200/")
public class OperationRestController {
  private AccountOperationsService accountOperationsService;

  @PostMapping("/accounts/{accountId}/operation/{type}")
  public AccountOperationDTO saveOperation(@RequestBody AccountOperationDTO accountOperation, @PathVariable(name = "accountId") String accountId, @PathVariable(name = "type") String type) throws IllegalOperationTypeException, BankAccountNotFoundException, InsufficientBalanceException {
    return accountOperationsService.saveOperation(accountId, accountOperation.getAmount(), accountOperation.getDescription(), type);
  }

  @PostMapping("/accounts/transfer")
  public void saveOperation(@RequestBody TransferDTO transferDTO) throws IllegalOperationTypeException, BankAccountNotFoundException, InsufficientBalanceException {
    accountOperationsService.transfer(transferDTO.getAccountIdSrc(), transferDTO.getAccountIdDest(), transferDTO.getAmount());
  }
}
