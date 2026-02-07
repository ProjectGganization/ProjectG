package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_sessions")
public class SalesSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    // Foreign key to User (seller)
    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    // Start and end times of the sales session
    @Column(name = "start_time")
    private LocalDateTime startTime;

    // End time can be null if the session is still active
    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Constructors
    public SalesSession() {
    }

    public SalesSession(Integer sellerId, LocalDateTime startTime, LocalDateTime endTime) {
        this.sellerId = sellerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
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
}