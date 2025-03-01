package com.sebadevbank.cards.service.impl;

import com.sebadevbank.cards.dto.CardsDto;
import com.sebadevbank.cards.dto.CardsRequestDto;
import com.sebadevbank.cards.entity.Cards;
import com.sebadevbank.cards.exception.CardAlreadyExistsException;
import com.sebadevbank.cards.exception.ResourceNotFoundException;
import com.sebadevbank.cards.mapper.CardsMapper;
import com.sebadevbank.cards.repository.CardsRepository;
import com.sebadevbank.cards.service.ICardService;
import com.sebadevbank.cards.service.client.AccountsFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardsRepository cardsRepository;
    private AccountsFeignClient accountsFeignClient;

    @Override
    public List<CardsDto> getCardsByAccountId(Long accountId) {
        List<Cards> cards = cardsRepository.findByAccountId(accountId);

        if (cards.isEmpty()){
            boolean accountExists= accountsFeignClient.checkAccountExists(accountId).getBody();
            if (!accountExists){
                throw new RuntimeException("La cuenta con ID " + accountId + " no existe.") ;
            }
            return List.of();
        }

        return cards.stream()
                .map(c->CardsMapper.mapToCardsDto(c,new CardsDto())).collect(Collectors.toList());
    }

    @Override
    public Cards getCardById(Long accountId, Long cardId) {
        return null;
    }

    @Override
    public CardsDto createCard(Long accountId, CardsRequestDto request) {
        boolean accountExists = accountsFeignClient.checkAccountExists(accountId).getBody();

        if (!accountExists){
            throw new ResourceNotFoundException("La Cuenta con ID "+accountId+" no existe");
        }

        if(cardsRepository.existsByCardNumber(request.getCardNumber())){
            throw new CardAlreadyExistsException("Esta tarjeta ya está asociada a otra cuenta");
        }

        Cards card = new Cards();
        card.setCardNumber(request.getCardNumber());
        card.setCardHolderName(request.getCardHolderName());
        card.setCardType(request.getCardType());
        card.setExpirationDate(request.getExpirationDate());
        card.setAccountId(accountId);

        Cards savedCard = cardsRepository.save(card);

        return CardsMapper.mapToCardsDto(savedCard, new CardsDto());
    }

    @Override
    public void deleteCard(Long accountId, Long cardId) {
        //verificar si existe card id
        Cards card = cardsRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFoundException("No se encontro la Tarjeta con ID: "+cardId)
        );
        //verificar que la tarjeta pertenezca a la cuenta
        if (!card.getAccountId().equals(accountId)){
            throw new ResourceNotFoundException("La Tarjeta no esta asociada a esta cuenta");
        }
        //eliminar card
        cardsRepository.delete(card);
    }
}
