package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    // Foreign key to TicketType
    @NotNull
    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    // Foreign key to Order
    @NotNull
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    // Unique QR code for the ticket
    @NotNull
    @Column(name = "qr_code", nullable = false, length = 250, unique = true)
    private String qrCode;

    // Ticket status (e.g., "Available", "Sold", "Reserved")
    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Constructors
    public Ticket() {
    }

    public Ticket(Integer typeId, Integer orderId, String qrCode, String status) {
        this.typeId = typeId;
        this.orderId = orderId;
        this.qrCode = qrCode;
        this.status = status;
    }

    // Getters and Setters
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}