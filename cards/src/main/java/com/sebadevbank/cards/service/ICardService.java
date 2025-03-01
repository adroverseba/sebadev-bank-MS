package com.sebadevbank.cards.service;

import com.sebadevbank.cards.dto.CardsDto;
import com.sebadevbank.cards.dto.CardsRequestDto;
import com.sebadevbank.cards.entity.Cards;

import java.util.List;

public interface ICardService {
    List<CardsDto> getCardsByAccountId(Long accountId);
    Cards getCardById(Long accountId, Long cardId);
    CardsDto createCard(Long accountId, CardsRequestDto request);
    void deleteCard(Long accountId, Long cardId );
}
