package com.example.week7.service;

import com.example.week7.model.User;
import com.example.week7.model.UserRegistration;
import com.example.week7.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Set<User> getUsers() {
        return userRepository.getUsers();
    }

    public void registerUser(UserRegistration userReg) {
        User user = new User(
            userReg.getFirstName(),
            userReg.getLastName(),
            userReg.getEmail(),
            userReg.getPassword(),
            LocalDateTime.now()
        );
        if (!userRepository.addUser(user)) {
            throw new IllegalArgumentException("User with that email already exists");
        }
    }

    public void loginUser(String email, String password) {
        if (!userRepository.loginUser(email, password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public void removeUser(String email, String password) {
        User user = new User(null, null, email, password, null);
        if (!userRepository.loginUser(email, password)) {
            throw new IllegalArgumentException("Login in with the correct credentials to delete user");
        }
        if (!userRepository.removeUser(user)) {
            throw new IllegalStateException("Can't delete user");
        }
    }
}
