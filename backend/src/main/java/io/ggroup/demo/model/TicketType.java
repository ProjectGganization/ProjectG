package io.ggroup.demo.model;

import jakarta.persistence.*;

// Tämä luokka kuvaa tietokannan taulua tickettype
// Taulussa on lippujen tyypit, jotka määrittää admin

@Entity
@Table(name = "tickettype")
public class TicketType {

    @Id
    @Column(name = "ticket_type", length = 100)
    private String ticketType;

    public TicketType() {
    }

    public TicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketType() {
        return ticketType;
    }
}