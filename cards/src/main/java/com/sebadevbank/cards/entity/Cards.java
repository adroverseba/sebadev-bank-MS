package com.sebadevbank.cards.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore //no exponemos el numero real en las respuestas
    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber; // verificar almacenamiento formato enmascarado o hash

    @Column(nullable = false)
    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType; // CREDIT/DEBIT

    @Column(nullable = false, length = 5)
    private String expirationDate; //Formato MM/YY

    @Column(nullable = false)
    private Long accountId; //referencia a la cuenta

    //meotod para obtener solo los ultimos 4 digitos en la respuesta
    public String getMaskedCardNumber(){
        return "**** **** **** "+cardNumber.substring(cardNumber.length()-4);
    }
}
