package com.sebadevbank.cards.dto;

import com.sebadevbank.cards.entity.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


//solo para POST /accounts/{ID}/cards
@Getter @Setter
public class CardsRequestDto {
    @NotNull(message = "El número de tarjeta es obligatorio")
    @Size(min = 16, max = 16, message = "El número de tarjeta debe tener 16 dígitos")
    @Pattern(regexp = "\\d{16}", message = "El número de tarjeta solo puede contener dígitos")
    private String cardNumber;

    @NotBlank(message = "El nombre del titular es obligatorio")
    @Size(min=3,message = "El nombre del titular debe tener al menos 3 caracteres")
    private String cardHolderName;

    @NotNull(message = "El tipo de tarjeta es obligatorio")
    private CardType cardType;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Formato de fecha inválido, debe ser MM/YY")
    private String expirationDate;

//    @NotNull(message = "La cuenta asociada es obligatoria")
//    private Long accountId;
}
