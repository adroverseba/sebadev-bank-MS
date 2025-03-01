package com.sebadevbank.usersservice.service;

import com.sebadevbank.usersservice.dto.User;
import com.sebadevbank.usersservice.repository.KeycloakUserRepository;
import com.sebadevbank.usersservice.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
        List<String> words = readWordsFromFiles("alias_words.txt");
        Random random = new Random();
        return words.get(random.nextInt(words.size()))+"."+
                words.get(random.nextInt(words.size()))+"."+
                words.get(random.nextInt(words.size()));
    }

    private List<String> readWordsFromFiles(String filePath) {
        //try-with-resources: esto me permite que InputStream y BufferedReader se cierre automaticamente al terminar el bloque try
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);//busco el archivo en el classpath
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//
        ) {
            //verifica la existencia del archivo
            if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo: " + filePath);
            }

            return reader.lines()//retorna un stream de lineas
                    .collect(Collectors.toList());//devuelve un List<String>
        }catch(IOException e){
            throw new RuntimeException("Error leyendo archivo de palabras para alias",e);
        }
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

    public void deleteUserById(String userId){
        keycloakUserRepository.deleteById(userId);
    }

    public User updateUser(String userId, User updatedUser){
        keycloakUserRepository.updateUser(userId,updatedUser);

        return findById(userId);
    }

}
