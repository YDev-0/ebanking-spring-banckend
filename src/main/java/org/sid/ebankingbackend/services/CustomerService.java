package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.CustomerDetailsDTO;
import org.sid.ebankingbackend.enums.SaveType;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.EditCustomerWithNullIdException;

import java.util.List;

public interface CustomerService {
  List<CustomerDTO> getCustomers();
  CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
  CustomerDTO saveCustomer(CustomerDTO customerDTO, SaveType saveType) throws CustomerNotFoundException, EditCustomerWithNullIdException;
  void deleteCustomer(Long customerId);

  List<CustomerDTO> searchCustomers(String keyword, String option);

  CustomerDetailsDTO getCustomerDetails(Long customerId) throws CustomerNotFoundException;
}
