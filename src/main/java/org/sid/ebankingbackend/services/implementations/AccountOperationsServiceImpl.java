package org.sid.ebankingbackend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.TransferDTO;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.enums.AccountType;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.IllegalOperationTypeException;
import org.sid.ebankingbackend.exceptions.InsufficientBalanceException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.services.AccountOperationsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationsServiceImpl implements AccountOperationsService {
  BankAccountRepository bankAccountRepository;
  AccountOperationRepository accountOperationRepository;
  BankAccountMapperImpl bankAccountMapper;

  @Override
  public List<AccountOperationDTO> getBankAccountOperations(String accountId) throws BankAccountNotFoundException {
    List<AccountOperationDTO> accountOperationDTOList = accountOperationRepository.findAllByBankAccountId(accountId).stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
      return accountOperationDTOList;
  }

  @Override
  public AccountHistoryDTO getBankAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
    BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException());
    Page<AccountOperation> accountOperations = accountOperationRepository.findAllByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
    AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
    List<AccountOperationDTO> accountOperationDTOList = accountOperations.getContent().stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    accountHistoryDTO.setAccountOperations(accountOperationDTOList);
    if(bankAccount instanceof CurrentAccount)
      accountHistoryDTO.setType(AccountType.CURRENT.name());
    else
      accountHistoryDTO.setType(AccountType.SAVING.name());
    accountHistoryDTO.setBalance(bankAccount.getBalance());
    accountHistoryDTO.setStatus(bankAccount.getStatus());
    accountHistoryDTO.setCurrentPage(page);
    accountHistoryDTO.setPageSize(size);
    accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
    return accountHistoryDTO;
  }

  @Override
  public AccountOperationDTO saveOperation(String accountId, double amount, String desc, String type) throws BankAccountNotFoundException, InsufficientBalanceException, IllegalOperationTypeException {
    BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException());
    OperationType operationType;

    try {
      operationType = OperationType.valueOf(type.toUpperCase());
    } catch(IllegalArgumentException e) {
      throw new IllegalOperationTypeException(type);
    }

    if(operationType == OperationType.DEBIT && bankAccount.getBalance() <= amount) throw new InsufficientBalanceException(this.getClass().getName(), 99);

    AccountOperation accountOperation = new AccountOperation();
    accountOperation.setType(operationType);
    accountOperation.setAmount(amount);
    accountOperation.setDescription(desc);
    accountOperation.setOperationDate(new Date());
    accountOperation.setBankAccount(bankAccount);
    accountOperationRepository.save(accountOperation);

    bankAccount.setBalance(operationType == OperationType.DEBIT ? bankAccount.getBalance() - amount : bankAccount.getBalance() + amount);
    bankAccountRepository.save(bankAccount);

    return bankAccountMapper.fromAccountOperation(accountOperation);
  }

  @Override
  public TransferDTO transfer(String accountIdSrc, String accountIdDest, double amount) throws BankAccountNotFoundException, InsufficientBalanceException, IllegalOperationTypeException {
    BankAccount bankAccountSrc = bankAccountRepository.findById(accountIdSrc).orElseThrow(() -> new BankAccountNotFoundException());
    BankAccount bankAccountDest = bankAccountRepository.findById(accountIdDest).orElseThrow(() -> new BankAccountNotFoundException());

    saveOperation(accountIdSrc, amount, "transfer to " + bankAccountDest, OperationType.DEBIT.name());
    saveOperation(accountIdDest, amount, "transfer from " + bankAccountSrc, OperationType.CREDIT.name());

    TransferDTO transferDTO = new TransferDTO();
    transferDTO.setAccountIdSrc(bankAccountSrc.getId());
    transferDTO.setAccountIdDest(bankAccountDest.getId());
    transferDTO.setAmount(amount);
    transferDTO.setDescription("transfer from " + bankAccountSrc + " to " + bankAccountDest);
    transferDTO.setDateTransfer(new Date());

    return transferDTO;
  }
}
