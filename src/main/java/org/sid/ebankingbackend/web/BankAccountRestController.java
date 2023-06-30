package org.sid.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.enums.AccountType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.services.AccountOperationsService;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
public class BankAccountRestController {
  private BankAccountService bankAccountService;
  private AccountOperationsService accountOperationsService;

  @GetMapping("/accounts")
  public List<BankAccountDTO> getBankAccounts() {
    return bankAccountService.getBankAccounts();
  }

  @GetMapping("/accounts/{accountId}")
  public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
    return bankAccountService.getBankAccount(accountId);
  }

  @GetMapping("/accounts/{accountId}/operations")
  public List<AccountOperationDTO> getBankAccountOperations(@PathVariable String accountId) throws BankAccountNotFoundException {
    return accountOperationsService.getBankAccountOperations(accountId);
  }

  @GetMapping("/accounts/{accountId}/history")
  public AccountHistoryDTO getBankAccountHistory(@PathVariable String accountId, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "10") int size) throws BankAccountNotFoundException {
    if(page < 1) throw new IllegalArgumentException("Page index must not be less than one");
    if(size < 5) throw new IllegalArgumentException("Page index must not be less than five");
    return accountOperationsService.getBankAccountHistory(accountId, page - 1, size);
  }

  @PostMapping("/accounts")
  public BankAccountDTO saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO) throws CustomerNotFoundException {
    if(bankAccountDTO.getType() == AccountType.CURRENT.name()) {
      CurrentBankAccountDTO currentBankAccountDTO = ((CurrentBankAccountDTO) bankAccountDTO);
      return bankAccountService.saveCurrentBankAccount(currentBankAccountDTO.getBalance(), currentBankAccountDTO.getOverDraft(), currentBankAccountDTO.getCustomer().getId());
    }

    SavingBankAccountDTO savingBankAccountDTO = ((SavingBankAccountDTO) bankAccountDTO);
    return bankAccountService.saveSavingBankAccount(savingBankAccountDTO.getBalance(), savingBankAccountDTO.getInterestRate(), savingBankAccountDTO.getCustomer().getId());
  }


}
