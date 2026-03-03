package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    // Foreign key to Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // Foreign key to Seller
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    private Seller seller;

    @Column(name = "is_refunded")
    private Boolean isRefunded;

    @Column(name = "is_paid")
    private Boolean isPaid;

    // Foreign key to PaymentMethod
    @ManyToOne
    @JoinColumn(name = "payment_method", referencedColumnName = "payment_method")
    private PaymentMethod paymentMethod;

    // Constructors
    public Order() {
    }

    public Order(Customer customer, LocalDateTime date, Seller seller, Boolean isRefunded, Boolean isPaid, PaymentMethod paymentMethod) {
        this.customer = customer;
        this.date = date;
        this.seller = seller;
        this.isRefunded = isRefunded;
        this.isPaid = isPaid;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean getIsRefunded() {
        return isRefunded;
    }

    public void setIsRefunded(Boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}