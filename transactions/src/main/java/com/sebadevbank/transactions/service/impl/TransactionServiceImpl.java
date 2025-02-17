package com.sebadevbank.transactions.service.impl;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.dto.UpdateBalanceRequest;
import com.sebadevbank.transactions.entity.Transaction;
import com.sebadevbank.transactions.mapper.TransactionMapper;
import com.sebadevbank.transactions.repository.TransactionRepository;
import com.sebadevbank.transactions.service.ITransactionService;
import com.sebadevbank.transactions.service.client.AccountFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final AccountFeignClient accountFeignClient;
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionDto> getLastTransactions(Long accountNumber, int limit) {
        List<Transaction> lastTransactions = transactionRepository
                .findTopNByAccountIdOrderByTimestampDesc(accountNumber, PageRequest.of(0,limit))
                .getContent();//PageRequest.of(0, limit) para pasar dinámicamente el límite de transacciones.
        return lastTransactions.stream()
                .map(t->TransactionMapper.mapToTransactionDto(t,new TransactionDto()))
                .collect(Collectors.toList())
        ;
    }

    @Override
    public List<TransactionDto> getAllTransactions(Long accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountNumber);
        return transactions.stream()
                .map(t-> TransactionMapper.mapToTransactionDto(
                        t,new TransactionDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        //guardo TX en DB
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, new Transaction());
        Transaction savedTransaction = transactionRepository.save(transaction);

        //Notificar a Account para actualizar saldo
        UpdateBalanceRequest updateBalance = new UpdateBalanceRequest(
                savedTransaction.getAccountId(),
                savedTransaction.getAmount(),
                savedTransaction.getTransactionType()
        );

        //comunicacion con Account MS
        accountFeignClient.updateAccountBalance(updateBalance);

        return TransactionMapper.mapToTransactionDto(savedTransaction,new TransactionDto());
    }
}
