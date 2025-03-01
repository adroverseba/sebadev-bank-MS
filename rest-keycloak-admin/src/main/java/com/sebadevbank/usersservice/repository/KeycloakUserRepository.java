package com.sebadevbank.usersservice.repository;

import com.sebadevbank.usersservice.dto.User;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Map;

public interface KeycloakUserRepository {
    User findById(String userId);

    List<User> findByUsername(String username);

    String registerUser(UserRepresentation user);

    void updateUserAttributes(String userId, Map<String, String> attributes);

    void deleteById(String userId);

    boolean logoutUserById(String userId);

    boolean logoutUserByToken(String userId);

    void updateUser(String userId, User updatedUser);
}
