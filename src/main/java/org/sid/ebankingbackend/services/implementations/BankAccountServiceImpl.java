package org.sid.ebankingbackend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.InsufficientBalanceException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
  private CustomerRepository customerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;
  private BankAccountMapperImpl bankAccountMapper;

  @Override
  public BankAccountDTO saveCurrentBankAccount(double initBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
    CurrentAccount bankAccount = new CurrentAccount();
    Customer customer = customerRepository.findById(customerId).orElse(null);

    if(customer == null) throw new CustomerNotFoundException();

    bankAccount.setId(UUID.randomUUID().toString());
    bankAccount.setCreatedAt(new Date());
    bankAccount.setBalance(initBalance);
    bankAccount.setCustomer(customer);
    bankAccount.setOverDraft(overDraft);

    bankAccountRepository.save(bankAccount);

    return bankAccountMapper.fromBankAccount(bankAccount);
  }

  @Override
  public BankAccountDTO saveSavingBankAccount(double initBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
    SavingAccount bankAccount = new SavingAccount();
    Customer customer = customerRepository.findById(customerId).orElse(null);

    if(customer == null) throw new CustomerNotFoundException();

    bankAccount.setId(UUID.randomUUID().toString());
    bankAccount.setCreatedAt(new Date());
    bankAccount.setBalance(initBalance);
    bankAccount.setCustomer(customer);
    bankAccount.setInterestRate(interestRate);

    bankAccountRepository.save(bankAccount);

    return bankAccountMapper.fromBankAccount(bankAccount);
  }

  @Override
  public List<BankAccountDTO> getBankAccounts(){
    List<BankAccountDTO> bankAccountDTOList = bankAccountRepository.findAll().stream().map(bankAccount -> bankAccountMapper.fromBankAccount(bankAccount)).collect(Collectors.toList());
    return bankAccountDTOList;
  }

  @Override
  public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
    BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException());
    return bankAccountMapper.fromBankAccount(bankAccount);
  }
}
