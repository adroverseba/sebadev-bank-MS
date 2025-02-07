package com.sebadevbank.accounts.entity;

import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Accounts {
    private Long id;
    private Long accountNumber;
    private String ownerName;
    private BigDecimal balance;
    @CreatedDate
    private LocalDateTime createdAt;
}
