package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  List<Customer> findByNameContains(String name);
  List<Customer> findByEmailContains(String email);
  List<Customer> findByNameContainsOrEmailContains(String name, String email);
}
