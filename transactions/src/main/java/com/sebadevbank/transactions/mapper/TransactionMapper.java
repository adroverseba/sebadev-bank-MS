package com.sebadevbank.transactions.mapper;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction, TransactionDto transactionDto) {
        transactionDto.setAccountId(transaction.getAccountId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());
        return transactionDto;
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto, Transaction transaction){
        transaction.setAccountId(transactionDto.getAccountId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        return transaction;
    }
}

