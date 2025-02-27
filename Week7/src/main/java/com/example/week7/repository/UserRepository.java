package com.example.week7.repository;

import com.example.week7.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepository {

    private final Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean loginUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }
}
