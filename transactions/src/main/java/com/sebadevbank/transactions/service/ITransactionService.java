package com.sebadevbank.transactions.service;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.entity.Transaction;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface ITransactionService {
    List<TransactionDto> getLastTransactions(Long accountNumber, int limit);
    List<TransactionDto> getAllTransactions(Long accountNumber);
    TransactionDto getActivity(Long accountNumber,Long transferId, Jwt jwt);
    TransactionDto createTransaction(TransactionDto transactionDto);
}
