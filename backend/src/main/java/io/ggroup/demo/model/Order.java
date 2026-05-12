package io.ggroup.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "is_refunded", nullable = false)
    private boolean isRefunded = false; 

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = true;

    @ManyToOne
    @JoinColumn(name = "paymentmethod")
    private PaymentMethod paymentMethod;

    // Constructors
    public Order() {
    }

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    public Order(Customer customer, LocalDateTime date, boolean isRefunded, boolean isPaid, PaymentMethod paymentMethod) {
        this.customer = customer;
        this.date = date;
        this.isRefunded = isRefunded;
        this.isPaid = isPaid;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean getIsRefunded() {
        return isRefunded;
    }

    public void setIsRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}