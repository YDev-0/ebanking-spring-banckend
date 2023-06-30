package org.sid.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor
public class TransferDTO {
  private String accountIdSrc;
  private String accountIdDest;
  private double amount;
  private Date dateTransfer;
  private String description;
}
