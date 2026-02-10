package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "event_status")
public class EventStatus {
    
    @Id
    @NotNull
    @Column(name = "event_status", nullable = false)
    private String eventStatus;

    // Constructors
    public EventStatus() {
    }

    public EventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    // Getters and Setters
    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
