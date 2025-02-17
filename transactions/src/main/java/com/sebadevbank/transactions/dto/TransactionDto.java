package com.sebadevbank.transactions.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long accountId;  // Relación con accounts (sin FK real, solo referencia)

    private BigDecimal amount;

    private String transactionType;

}
