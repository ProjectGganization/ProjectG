package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "issuedtickets")
public class IssuedTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuedticket_id")
    private Integer issuedTicketId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false )
    private Order order;

    @NotNull
    @Column(name = "qr_code", nullable = false, unique = true, length = 250)
    private String qrCode;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "used_at", nullable = false)
    private boolean used = false;

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

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }
}
