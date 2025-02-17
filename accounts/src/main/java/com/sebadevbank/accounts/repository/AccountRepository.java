package com.sebadevbank.accounts.repository;

import com.sebadevbank.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Accounts,Long> {
}
