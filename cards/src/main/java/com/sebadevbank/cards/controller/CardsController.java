package com.sebadevbank.cards.controller;

import com.sebadevbank.cards.dto.CardsDto;
import com.sebadevbank.cards.dto.CardsRequestDto;
import com.sebadevbank.cards.entity.Cards;
import com.sebadevbank.cards.service.ICardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/cards")
@RequiredArgsConstructor
public class CardsController {
    private final ICardService iCardService;

    @GetMapping
    public ResponseEntity<List<CardsDto>> getCards(@PathVariable Long accountId){
        List<CardsDto> cardsDto = iCardService.getCardsByAccountId(accountId);
        return ResponseEntity.ok(cardsDto);
    }

    @PostMapping
    public ResponseEntity<CardsDto> createCard(@PathVariable Long accountId,@Valid @RequestBody CardsRequestDto request){
        return new ResponseEntity<>(iCardService.createCard(accountId,request),HttpStatus.CREATED);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long accountId, @PathVariable Long cardId){
        iCardService.deleteCard(accountId,cardId);
        return ResponseEntity.ok().build();
    }

}
