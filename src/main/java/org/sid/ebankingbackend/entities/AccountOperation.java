package org.sid.ebankingbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.enums.OperationType;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class
AccountOperation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date operationDate;
  private double amount;
  @Enumerated(EnumType.STRING)
  private OperationType type;
  private String Description;
  @ManyToOne
  private BankAccount bankAccount;
}
