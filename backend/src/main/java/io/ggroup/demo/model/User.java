package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @NotNull
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Date when the account was created
    @Column(name = "account_created")
    private LocalDate accountCreated;

    // Foreign key to AccountStatus
    @ManyToOne
    @JoinColumn(name = "account_status", referencedColumnName = "account_status")
    private AccountStatus accountStatus;

    // Constructors
    public User() {
    }

    public User(String email, String passwordHash, LocalDate accountCreated, AccountStatus accountStatus) {
        this.email = email; 
        this.passwordHash = passwordHash;
        this.accountCreated = accountCreated;
        this.accountStatus = accountStatus;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(LocalDate accountCreated) {
        this.accountCreated = accountCreated;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}