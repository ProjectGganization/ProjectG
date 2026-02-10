package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    // Foreign key to TicketType
    @ManyToOne
    @JoinColumn(name = "ticket_type", referencedColumnName = "ticket_type")
    private TicketType ticketType;

    // Foreign key to Event
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    @NotNull
    @Column(name = "unitprice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @Column(name = "in_stock", nullable = false)
    private Integer inStock;

    @NotNull
    @Column(name = "order_limit", nullable = false)
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

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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