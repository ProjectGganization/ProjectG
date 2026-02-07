package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @NotNull
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    // Foreign key to Venue
    @NotNull
    @Column(name = "venue_id", nullable = false)
    private Integer venueId;

    // Event status (e.g., "Visible", "Hidden")
    @Column(name = "status", length = 50)
    private String status = "Visible";

    // Constructors
    public Event() {
    }

    public Event(String title, String description, LocalDateTime startTime, Integer venueId, String status) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.venueId = venueId;
        this.status = status;
    }

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}