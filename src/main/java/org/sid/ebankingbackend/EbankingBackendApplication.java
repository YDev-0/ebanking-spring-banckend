package org.sid.ebankingbackend;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.enums.SaveType;
import org.sid.ebankingbackend.exceptions.*;
import org.sid.ebankingbackend.services.AccountOperationsService;
import org.sid.ebankingbackend.services.BankAccountService;
import org.sid.ebankingbackend.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@AllArgsConstructor
public class EbankingBackendApplication {
  private BankAccountService bankAccountService;
  private AccountOperationsService accountOperationsService;
  private CustomerService customerService;

  private void fillDatabase() {
    Stream.of("Youness", "Yassine", "Redone").forEach(name -> {
      try {
        customerService.saveCustomer(new CustomerDTO(null, name, name + "yemail.net"), SaveType.ADD);
      } catch (CustomerNotFoundException | EditCustomerWithNullIdException e) {
        System.err.println(e);
      }
    });

    customerService.getCustomers().forEach(customer -> {
      int rand = new Random().nextInt(1, 3);
      if(rand >= 1) {
        try {
          bankAccountService.saveCurrentBankAccount(new Random().nextDouble(0, 9999999), 9000, customer.getId());
        } catch (CustomerNotFoundException e) {
          System.err.println(e);
        }
      }
      if(rand >= 2) {
        try {
          bankAccountService.saveSavingBankAccount(new Random().nextDouble(0, 9999999), 9000, customer.getId());
        } catch (CustomerNotFoundException e) {
          System.err.println(e);
        }
      }
    });

    bankAccountService.getBankAccounts().forEach(bankAccount -> {
      for (int i = 1; i <= 10; i++) {
        try {
          accountOperationsService.saveOperation(bankAccount.getId(), Math.random() * 99999, "test ", new Random().nextBoolean() ? OperationType.DEBIT.name() : OperationType.CREDIT.name());
        } catch (BankAccountNotFoundException | IllegalOperationTypeException | InsufficientBalanceException e) {
          System.err.println(e);
        }
      }
    });
  }

  public static void main(String[] args) {
    SpringApplication.run(EbankingBackendApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner() {
    return args -> {
//      fillDatabase();
    };
  }

}
