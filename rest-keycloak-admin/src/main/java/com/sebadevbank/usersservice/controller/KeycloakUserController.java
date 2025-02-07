package com.sebadevbank.usersservice.controller;

import com.sebadevbank.usersservice.dto.User;
import com.sebadevbank.usersservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class KeycloakUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping("/search/{username}")
    public List<User> getUserByUsername(@PathVariable String username) {
        return userService.findByName(username);
    }

    @PostMapping("/{userId}/logout")
    public ResponseEntity<String> logoutUser(@PathVariable String userId) {
        userService.logoutUser(userId);
        return ResponseEntity.ok("Sesión cerrada para el usuario con ID: "+userId);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        return userService.logoutUserByToken(request);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody UserRepresentation userRepresentation){
        User registeredUser = userService.registerUser(userRepresentation);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("Usuario con ID: "+userId+" eliminado con éxito");
    }


}
