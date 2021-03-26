package cat.tecnocampus.users.application.service;

import cat.tecnocampus.users.application.portsOut.UserDAO;
import cat.tecnocampus.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCases implements cat.tecnocampus.users.application.portsIn.UserUseCases {

    private final UserDAO userDAO;

    public UserUseCases(UserDAO UserDAO) {
        this.userDAO = UserDAO;
    }

    @Override
    public User createUser(String username, String name, String secondName, String email) {
        User user = new User();
        user.setName(name);
        user.setSecondName(secondName);
        user.setUsername(username);
        user.setEmail(email);

        registerUser(user);
        return user;
    }

    @Override
    public int registerUser(User user) {
        return userDAO.insert(user);
    }

    @Override
    public User deleteUser(String username) {
        User user = new User();
        if (userExists(username)) {
            user = getUser(username);
            userDAO.delete(username);
            //TODO 3: envia un missatge per tal que el microservei de les notes esborri les notes d'aquest usuari
            // Pensa que també hauràs d'implementar la part de rebre el missatge (i esborrar les notes) al microservei de notes
            // Recorda de seguir l'arquitectura hexagonal amb els ports i els adaptadors
            return user;
        }

        return null;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(String userName) {
        return userDAO.findByUsername(userName);
    }

    @Override
    public boolean userExists(String username) {
        return userDAO.existsUser(username);
    }

}