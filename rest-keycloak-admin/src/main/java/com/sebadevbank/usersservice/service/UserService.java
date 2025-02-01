package com.sebadevbank.usersservice.service;

import com.sebadevbank.usersservice.model.User;
import com.sebadevbank.usersservice.repository.KeycloakUserRepository;
import com.sebadevbank.usersservice.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    private KeycloakUserRepository keycloakUserRepository;
    private TokenUtil tokenUtil;

    @Autowired
    public UserService(KeycloakUserRepository keycloakUserRepository,TokenUtil tokenUtil) {
        this.keycloakUserRepository = keycloakUserRepository;
        this.tokenUtil = tokenUtil;
    }


    public User registerUser(UserRepresentation userRepresentation) {
//        registrar usuario en keycloak
         String userId = keycloakUserRepository.registerUser(userRepresentation);
         if(userId ==null){
             throw new RuntimeException("Error al registrar el usuario");
         }
        // Generar CVU y Alias
        String cvu = generateCvu();
        String alias = generateAlias();

        //Guardar CVU y Alias en Keycloak como atributos personalizados
        keycloakUserRepository.updateUserAttributes(userId, Map.of("cvu",cvu,"alias",alias));

        // Obtener usuario actualizado
        User founduser = keycloakUserRepository.findById(userId);
        founduser.setAlias(alias);
        founduser.setCvu(cvu);

        // Retornar DTO con los datos
        return founduser;
    }

    private String generateAlias() {
        List<String> words = List.of("sol", "luz", "nube", "cielo", "estrella", "viento", "mar", "río",
                "rojo", "verde", "azul", "tigre", "león", "águila", "montaña", "fuego");
        Random random = new Random();
        return words.get(random.nextInt(words.size()))+"."+
                words.get(random.nextInt(words.size()))+"."+
                words.get(random.nextInt(words.size()));
    }

    private String generateCvu() {
        Random random = new Random();
        StringBuilder cvu = new StringBuilder();

        for(int i =0;i<22;i++){
            cvu.append(random.nextInt(10));// Agregar un número entre 0 y 9

        }
            return cvu.toString();
    }

    public User findById(String id) {
        return keycloakUserRepository.findById(id);
    }

    public List<User> findByName(String name) {
        return keycloakUserRepository.findByUsername(name);
    }

    public boolean logoutUser(String userId){
        return keycloakUserRepository.logoutUserById(userId);
    }

    public ResponseEntity<String> logoutUserByToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Token no válido");
        }

        String accessToken = authHeader.substring(7);
        String userId = tokenUtil.extractUserIdFromToken(accessToken);


        boolean success = keycloakUserRepository.logoutUserById(userId);

        return success ? ResponseEntity.ok("Sesión invalidada con éxito") :
                ResponseEntity.internalServerError().body("Error al invalidar sesión");
    }



}
