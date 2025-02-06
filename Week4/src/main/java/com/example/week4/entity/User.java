package com.example.week4.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    private boolean isAccountActivated = false;
    private boolean enabled = false;
    private boolean isEnable2factorAuthentication;

    private boolean isSuspended = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isAccountActivated() {
        return isAccountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        isAccountActivated = accountActivated;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnable2factorAuthentication() {
        return isEnable2factorAuthentication;
    }

    public void setEnable2factorAuthentication(boolean enable2factorAuthentication) {
        isEnable2factorAuthentication = enable2factorAuthentication;
    }
}
