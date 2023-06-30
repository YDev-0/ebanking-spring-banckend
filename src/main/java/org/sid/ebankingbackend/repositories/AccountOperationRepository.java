package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
  List<AccountOperation> findAllByBankAccountId(String accountId);
  Page<AccountOperation> findAllByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
