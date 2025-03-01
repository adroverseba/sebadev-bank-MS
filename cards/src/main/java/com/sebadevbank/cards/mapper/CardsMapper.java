package com.sebadevbank.cards.mapper;

import com.sebadevbank.cards.dto.CardsDto;
import com.sebadevbank.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto){
        cardsDto.setMaskedCardNumber(cards.getMaskedCardNumber());
        cardsDto.setCardHolderName(cards.getCardHolderName());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setExpirationDate(cards.getExpirationDate());
        cardsDto.setAccountId(cards.getAccountId());
        return cardsDto;
    }

//    public static Cards mapToCards(CardsDto cardsDto, Cards cards){
//        cards.setCardHolderName(cardsDto.getCardHolderName());
//        cards.setCardType(cardsDto.getCardType());
//        cards.setExpirationDate(cardsDto.getExpirationDate());
//        cards.setAccountId(cardsDto.getAccountId());
//        return cards;
//    }


}
