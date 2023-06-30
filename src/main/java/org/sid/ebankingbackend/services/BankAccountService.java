package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {

  BankAccountDTO saveCurrentBankAccount(double initBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
  BankAccountDTO saveSavingBankAccount(double initBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
  List<BankAccountDTO> getBankAccounts();

  BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
}
