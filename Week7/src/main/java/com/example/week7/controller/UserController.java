package com.example.week7.controller;

import com.example.week7.model.UserRegistration;
import com.example.week7.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistration user) {
        userService.registerUser(user);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        userService.loginUser(email, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String email, @RequestParam String password) {
        userService.removeUser(email, password);
        return ResponseEntity.ok().build();
    }
}
