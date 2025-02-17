package com.sebadevbank.accounts.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long accountId;  // Relación con accounts (sin FK real, solo referencia)

    private int amount;

    private String transactionType;

    private LocalDateTime timestamp;
}
