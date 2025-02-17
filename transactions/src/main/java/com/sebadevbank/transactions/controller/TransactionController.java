package com.sebadevbank.transactions.controller;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.service.ITransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping("/{accountNumber}/last")
    public ResponseEntity<List<TransactionDto>> getLastTransactions(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(transactionService.getLastTransactions(accountNumber,5));
    }

    @GetMapping("/{accountNumber}/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(transactionService.getAllTransactions(accountNumber));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }
}
