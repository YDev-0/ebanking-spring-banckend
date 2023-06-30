package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.TransferDTO;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.IllegalOperationTypeException;
import org.sid.ebankingbackend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface AccountOperationsService {
  public List<AccountOperationDTO> getBankAccountOperations(String accountId) throws BankAccountNotFoundException;

  AccountHistoryDTO getBankAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

  AccountOperationDTO saveOperation(String accountId, double amount, String desc, String operationType) throws BankAccountNotFoundException, InsufficientBalanceException, IllegalOperationTypeException;

  TransferDTO transfer(String accountIdSrc, String accountIdDest, double amount) throws BankAccountNotFoundException, InsufficientBalanceException, IllegalOperationTypeException;
}
