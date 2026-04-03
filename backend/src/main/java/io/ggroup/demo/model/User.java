package io.ggroup.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", nullable = false, length = 225, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 225)
    private String passwordHash;

    @Column(name = "account_created", nullable = false)
    private LocalDateTime accountCreated;

    @ManyToOne
    @JoinColumn(name = "account_status",  nullable = false)
    private AccountStatus accountStatus;

    // Constructors
    public User() {
    }

    public User(String email, String passwordHash, AccountStatus accountStatus) {
        this.email = email; 
        this.passwordHash = passwordHash;
        this.accountStatus = accountStatus;
    }

    @PrePersist
    protected void onCreate() {
        if (this.accountCreated == null) {
            this.accountCreated = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
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

    public LocalDateTime getAccountCreated() {
        return accountCreated;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}