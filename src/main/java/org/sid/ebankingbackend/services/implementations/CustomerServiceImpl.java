package org.sid.ebankingbackend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.enums.SaveType;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.EditCustomerWithNullIdException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
  private BankAccountMapperImpl bankAccountMapper;
  private CustomerRepository customerRepository;
  private BankAccountRepository bankAccountRepository;

  @Override
  public List<CustomerDTO> getCustomers() {
    return customerRepository.findAll().stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
  }

  @Override
  public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
    return bankAccountMapper.fromCustomer(customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException()));
  }

  @Override
  public CustomerDTO saveCustomer(CustomerDTO customerDTO, SaveType saveType) throws CustomerNotFoundException, EditCustomerWithNullIdException {
    if(saveType == SaveType.Edit) {
      if(customerDTO.getId() == null) throw new EditCustomerWithNullIdException();
      bankAccountMapper.fromCustomerDTO(getCustomer(customerDTO.getId()));
    }
    Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
    log.info("Saving customer : " + customer);
    return bankAccountMapper.fromCustomer(customerRepository.save(customer));
  }

  @Override
  public void deleteCustomer(Long customerId) {
    customerRepository.deleteById(customerId);
  }

  @Override
  public List<CustomerDTO> searchCustomers(String keyword, String option) {
    List<Customer> customers;
    if(option.equals("NAME"))
      customers = customerRepository.findByNameContains(keyword);
    else if(option.equals("EMAIL"))
      customers = customerRepository.findByEmailContains(keyword);
    else
      customers = customerRepository.findByNameContainsOrEmailContains(keyword, keyword);

    List<CustomerDTO> customerDTOList = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());

    return customerDTOList;
  }

  @Override
  public CustomerDetailsDTO getCustomerDetails(Long customerId) throws CustomerNotFoundException {
    Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException());
    List<BankAccount> bankAccounts = bankAccountRepository.findAllByCustomer(customer);
    List<BankAccountDTO> bankAccountsDTO = bankAccounts.stream().map(bankAccount -> bankAccountMapper.fromBankAccount(bankAccount)).collect(Collectors.toList());
    CustomerDetailsDTO customerDetailsDTO = bankAccountMapper.fromCustomerGetCustomerDetailsDTO(customer);
    bankAccountsDTO.forEach(bankAccountDTO -> {
      if (bankAccountDTO instanceof CurrentBankAccountDTO) {
        customerDetailsDTO.getCurrentAccounts().add(bankAccountDTO);
      } else {
        customerDetailsDTO.getSavingAccounts().add(bankAccountDTO);
      }
    });

    return customerDetailsDTO;
  }
}
