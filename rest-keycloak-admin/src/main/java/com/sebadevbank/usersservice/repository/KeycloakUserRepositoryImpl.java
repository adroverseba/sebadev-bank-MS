package com.sebadevbank.usersservice.repository;

import com.sebadevbank.usersservice.dto.User;
import com.sebadevbank.usersservice.exception.UserNotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class KeycloakUserRepositoryImpl implements KeycloakUserRepository {
    private static final Logger log = LoggerFactory.getLogger(KeycloakUserRepositoryImpl.class);
    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

//    public KeycloakUserRepositoryImpl(Keycloak keycloak) {
//        this.keycloak = keycloak;
//    }

    /**
     * @param userId
     * @return
     */
    @Override
    public User findById(String userId) {
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(userId).toRepresentation();

        return fromRepresentation(userRepresentation);
    }

    /**
     * @param username
     * @return
     */
    @Override
    public List<User> findByUsername(String username) {
        try {
            log.info("Fetching user by username {}", username);
            String accessToken = keycloak.tokenManager().getAccessTokenString();
            System.out.println("Token obtenido: " + accessToken);
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> userRepresentation = usersResource.search(username, true);

            if (userRepresentation.isEmpty()){
                log.warn("No users found with username:{}",username);
                throw new UserNotFoundException("No se encontró el usuario con username: "+username);
            }

            return userRepresentation.stream().map(user -> fromRepresentation(user)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching user by username {}", username, e);
            throw new RuntimeException("Error fetching user from keycloak", e);
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    public String registerUser(UserRepresentation user) {
        UsersResource userResource = keycloak.realm(realm).users();
        Response response = userResource.create(user);

//      it extracts the Location header from the response, which typically contains the URL of the newly created resource. It then returns the ID of the created user by extracting the last part of the URL
        if (response.getStatus() == 201){
            String locationHeader = response.getHeaderString("Location");
            return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
        }else{
            return null;
        }
    }

    public void updateUserAttributes(String userId, Map<String, String> attributes) {
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();

        // Agregar nuevos atributos
        if (userRepresentation.getAttributes() == null) {
            userRepresentation.setAttributes(new HashMap<>());
        }

        // Agregar o actualizar atributos personalizados
        attributes.forEach((key, value) -> userRepresentation.getAttributes().put(key, List.of(value)));

        // Actualizar usuario en Keycloak
        userResource.update(userRepresentation);
    }


    /**
     * @param userId
     */
    @Override
    public void deleteById(String userId) {
        try {
            //obtener el resource del usuario
            UserResource userResource = keycloak.realm(realm).users().get(userId);

            //Eliminar usuario
            userResource.remove();;
            log.info("Usuarion con ID: {} eliminado correctamente", userId);
        }catch(NotFoundException e){
            throw new UserNotFoundException("Usuario con Id: "+userId+" no existe");
        }catch(Exception e){
            throw new RuntimeException("Error al eliminar el usuario",e);
        }

    }

    /**
     * @param userId
     */
    @Override
    public boolean logoutUserById(String userId) {
        try{
        keycloak.realm(realm).users().get(userId).logout();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public boolean logoutUserByToken(String userId) {
        try {
            keycloak.realm(realm).users().get(userId).logout();
            return true;
        } catch (Exception e) {
            return false;

        }
    }


    private User fromRepresentation(UserRepresentation userRepresentation) {
        return new User(userRepresentation.getId(), userRepresentation.getFirstName(), userRepresentation.getLastName(), userRepresentation.getEmail());
    }

}
