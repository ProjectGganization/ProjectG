package io.ggroup.demo.model;

import jakarta.persistence.*;

// Tämä luokka kuvaa tietokannan taulua eventstatus
// Taulussa on vain yksi sarake, joka toimii myös taulun pääavaimena
// Ainoat mahdolliset arvot upcoming/ongoing/finished

@Entity
@Table(name = "eventstatus")
public class EventStatus {
    
    @Id
    @Column(name = "event_status")
    private String eventStatus;

    public EventStatus() {
    }

    public EventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventStatus() {
        return eventStatus;
    }
}