package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Integer venueId;

    @NotNull
    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false, length = 250)
    private String address;

    // Foreign key to PostalCode (optional, can be null)
    @ManyToOne
    @JoinColumn(name = "postal_code", referencedColumnName = "postal_code")
    private PostalCode postalCode;

    // Constructors
    public Venue() {
    }

    public Venue(String name, String address, PostalCode postalCode) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
    }

    // Getters and Setters
    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
}