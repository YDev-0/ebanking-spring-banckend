package org.sid.ebankingbackend.mappers;

import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.enums.AccountType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BankAccountMapperImpl {
  public CustomerDTO fromCustomer(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO);
    return customerDTO;
  }

  public Customer fromCustomerDTO(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDTO, customer);
    return customer;
  }

  public CustomerDetailsDTO fromCustomerGetCustomerDetailsDTO(Customer customer) {
    CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
    BeanUtils.copyProperties(customer, customerDetailsDTO);
    return customerDetailsDTO;
  }

  public Customer fromCustomerDetailsDTO(CustomerDetailsDTO customerDetailsDTO) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDetailsDTO, customer);
    customerDetailsDTO.getCurrentAccounts().stream().map(bankAccountDTO -> fromBankAccountDTO(bankAccountDTO)).collect(Collectors.toList()).forEach(bankAccount -> {
      customer.getBankAccounts().add(bankAccount);
    });
    customerDetailsDTO.getSavingAccounts().stream().map(bankAccountDTO -> fromBankAccountDTO(bankAccountDTO)).collect(Collectors.toList()).forEach(bankAccount -> {
      customer.getBankAccounts().add(bankAccount);
    });
    return customer;
  }

  public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
    BankAccountDTO bankAccountDTO = null;

    if(bankAccount instanceof CurrentAccount) {
      bankAccountDTO = new CurrentBankAccountDTO();
      bankAccountDTO.setType(AccountType.CURRENT.name());
    } else if(bankAccount instanceof SavingAccount) {
      bankAccountDTO = new SavingBankAccountDTO();
      bankAccountDTO.setType(AccountType.SAVING.name());
    }

    BeanUtils.copyProperties(bankAccount, bankAccountDTO);
    bankAccountDTO.setCustomer(fromCustomer(bankAccount.getCustomer()));

    return bankAccountDTO;
  }

  public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO) {
    BankAccount bankAccount = null;

    if(bankAccountDTO instanceof CurrentBankAccountDTO) {
      bankAccount = new CurrentAccount();
    } else if(bankAccountDTO instanceof SavingBankAccountDTO) {
      bankAccount = new SavingAccount();
    }

    BeanUtils.copyProperties(bankAccountDTO, bankAccount);
    bankAccount.setCustomer(fromCustomerDTO(bankAccountDTO.getCustomer()));

    return bankAccount;
  }

  public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
    AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
    BeanUtils.copyProperties(accountOperation, accountOperationDTO);
    return accountOperationDTO;
  }

  public AccountOperation fromAccountOpetionDTO(AccountOperationDTO accountOperationDTO) {
    AccountOperation accountOperation = new AccountOperation();
    BeanUtils.copyProperties(accountOperationDTO, accountOperation);
    return accountOperation;
  }
}
