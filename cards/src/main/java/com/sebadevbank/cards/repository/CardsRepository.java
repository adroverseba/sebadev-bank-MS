package com.sebadevbank.cards.repository;

import com.sebadevbank.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardsRepository extends JpaRepository<Cards, Long> {
    boolean existsByCardNumber(String cardNumber);
    List<Cards> findByAccountId(Long accountId);
}
