package com.sebadevbank.cards.dto;

import com.sebadevbank.cards.entity.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CardsDto {
    private String maskedCardNumber;
    private String cardHolderName;
    private CardType cardType;
    private String expirationDate;
    private Long accountId;

}
