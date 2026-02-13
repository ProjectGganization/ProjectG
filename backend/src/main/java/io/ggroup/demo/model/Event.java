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

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Foreign key to EventStatus
    @ManyToOne
    @JoinColumn(name = "event_status", referencedColumnName = "event_status")
    private EventStatus eventStatus;

    // Foreign key to Venue
    @NotNull
    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id")
    private Venue venue;

    // Foreign key to Category
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    private Category category;

    // Constructors
    public Event() {
    }

    public Event(String title, String description, byte[] photo, LocalDateTime startTime, LocalDateTime endTime, 
                 EventStatus eventStatus, Venue venue, Category category) {
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventStatus = eventStatus;
        this.venue = venue;
        this.category = category;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}