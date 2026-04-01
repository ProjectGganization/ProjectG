package io.ggroup.demo.model;

import jakarta.persistence.*;

// Tämä luokka kuvaa tietokannan taulua accountstatus
// Taulussa on vain yksi sarake, joka toimii myös taulun pääavaimena
// Ainoat mahdolliset arvot active/inactive

@Entity
@Table(name = "accountstatus")
public class AccountStatus {
    
    @Id
    @Column(name = "account_status")
    private String accountStatus;

    public AccountStatus() {
    }

    public AccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }
}