package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account_status")
public class AccountStatus {
    
    @Id
    @NotNull
    @Column(name = "account_status", nullable = false)
    private String accountStatus;

    // Constructors
    public AccountStatus() {
    }

    public AccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    // Getters and Setters
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}