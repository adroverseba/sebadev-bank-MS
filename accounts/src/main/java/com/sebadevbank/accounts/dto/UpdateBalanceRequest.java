package com.sebadevbank.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBalanceRequest {
    private Long accountId;
    private BigDecimal amount;
    private String transactionType;  // "CREDIT" o "DEBIT"
}
