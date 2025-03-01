package com.sebadevbank.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {

    @Column(name = "account_number")
    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "owner_name")
    private String ownerName;

    private BigDecimal balance;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
