package io.ggroup.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "unitprice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "in_stock", nullable = false)
    private Integer inStock;

    @Column(name = "order_limit")
    private Integer orderLimit;

    // Constructors
    public Ticket() {
    }

    public Ticket(TicketType ticketType, Event event, BigDecimal unitPrice, Integer inStock, Integer orderLimit) {
        this.ticketType = ticketType;
        this.event = event;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
        this.orderLimit = orderLimit;
    }

    // Getters and Setters
    public Integer getTicketId() {
        return ticketId;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }
}