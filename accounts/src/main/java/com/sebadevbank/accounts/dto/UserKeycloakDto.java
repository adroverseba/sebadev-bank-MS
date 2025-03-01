package com.sebadevbank.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserKeycloakDto {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String cvu;
        private String alias;

}
