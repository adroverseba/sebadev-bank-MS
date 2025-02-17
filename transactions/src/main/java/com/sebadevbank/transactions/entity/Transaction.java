package com.sebadevbank.transactions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;  // Relación con accounts (sin FK real, solo referencia)

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_type") // Ejemplo: "DEBIT", "CREDIT"
    private String transactionType;

    @CreatedDate
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
