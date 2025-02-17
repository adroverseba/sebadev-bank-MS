package com.sebadevbank.transactions.service;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.entity.Transaction;

import java.util.List;

public interface ITransactionService {
    List<TransactionDto> getLastTransactions(Long accountNumber, int limit);
    List<TransactionDto> getAllTransactions(Long accountNumber);
    TransactionDto createTransaction(TransactionDto transactionDto);
}
