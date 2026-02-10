package io.ggroup.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @Column(name = "payment_method")
    private String paymentMethod;

    // Constructors
    public PaymentMethod() {
    }

    public PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
