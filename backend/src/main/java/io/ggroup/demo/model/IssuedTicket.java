package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "issued_tickets")
public class IssuedTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_ticket_id")
    private Integer issuedTicketId;

    // Foreign key to Order
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @NotNull
    @Column(name = "qr_code", nullable = false, length = 250)
    private String qrCode;

    // Foreign key to Ticket
    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Ticket ticket;

    // Constructors
    public IssuedTicket() {
    }

    public IssuedTicket(Order order, String qrCode, Ticket ticket) {
        this.order = order;
        this.qrCode = qrCode;
        this.ticket = ticket;
    }

    // Getters and Setters
    public Integer getIssuedTicketId() {
        return issuedTicketId;
    }

    public void setIssuedTicketId(Integer issuedTicketId) {
        this.issuedTicketId = issuedTicketId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
