package com.sebadevbank.transactions.controller;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.service.ITransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/{accountNumber}/activity/{transferId}")
    public ResponseEntity<TransactionDto> getActivity(
            @PathVariable @Min(1) Long accountNumber,
            @PathVariable @Positive Long transferId,
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(transactionService.getActivity(accountNumber, transferId, jwt));
    }
}
