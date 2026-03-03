package io.ggroup.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @Column(name = "ticket_type", length = 100)
    private String ticketType;

    // Constructors
    public TicketType() {
    }

    public TicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    // Getters and Setters
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}