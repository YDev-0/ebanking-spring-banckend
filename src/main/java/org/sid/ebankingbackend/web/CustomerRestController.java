package org.sid.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.CustomerDetailsDTO;
import org.sid.ebankingbackend.enums.SaveType;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.EditCustomerWithNullIdException;
import org.sid.ebankingbackend.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
public class CustomerRestController {
  private CustomerService customerService;

  @GetMapping("/customers")
  public List<CustomerDTO> customersList() {
    return customerService.getCustomers();
  }

  @GetMapping("/customers/{id}")
  public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
    return customerService.getCustomer(customerId);
  }

  @GetMapping("/customers/search")
  public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword", defaultValue = "") String keyword, @RequestParam(name="option", defaultValue = "") String option) throws CustomerNotFoundException {
    return customerService.searchCustomers(keyword, option);
  }

  @PostMapping("/customers/save")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException, EditCustomerWithNullIdException {
    return customerService.saveCustomer(customerDTO, customerDTO.getId() != null ? SaveType.Edit : SaveType.ADD);
  }

  @PutMapping("/customers/save/{id}")
  public CustomerDTO saveCustomer(@PathVariable(name="id") Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException, EditCustomerWithNullIdException {
    customerDTO.setId(customerId);
    return customerService.saveCustomer(customerDTO, SaveType.Edit);
  }

  @DeleteMapping("/customers/delete/{id}")
  public void deleteCustomer(@PathVariable(name="id") Long customerId) {
    customerService.deleteCustomer(customerId);
  }

  @GetMapping("/customers/details/{id}")
  public CustomerDetailsDTO getCustomerDetails(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
    return customerService.getCustomerDetails(customerId);
  }
}
