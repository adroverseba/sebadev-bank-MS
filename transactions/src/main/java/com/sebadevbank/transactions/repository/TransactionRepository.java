package com.sebadevbank.transactions.repository;

import com.sebadevbank.transactions.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId ORDER BY t.timestamp DESC")
    Page<Transaction> findTopNByAccountIdOrderByTimestampDesc(@Param("accountId") Long accountId, Pageable pageable);

    List<Transaction> findByAccountId(Long accountId);

    Optional<Transaction> findByAccountIdAndId(Long accountId, Long d);
}
