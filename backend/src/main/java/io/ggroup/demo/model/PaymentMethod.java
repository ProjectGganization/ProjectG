package io.ggroup.demo.model;

import jakarta.persistence.*;

// Tämä luokka kuvaa tietokannan taulua paymentmethod
// Taulussa on vain yksi sarake, joka toimii myös taulun pääavaimena
// Ainoat mahdolliset arvot card/bank/cash

@Entity
@Table(name = "paymentmethod")
public class PaymentMethod {

    @Id
    @Column(name = "paymentmethod")
    private String paymentMethod;

    public PaymentMethod() {
    }

    public PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}