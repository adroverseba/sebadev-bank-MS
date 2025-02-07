package com.sebadevbank.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cvu;
    private String alias;

    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }
}
