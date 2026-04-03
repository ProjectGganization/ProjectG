package io.ggroup.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "postalcodes")
public class PostalCode {
    
    @Id
    @Column(name = "postalcode", length = 5)
    private String postalCode;

    @NotNull
    @Column(name = "city", nullable = false, length = 250)
    private String city;

    // Constructors
    public PostalCode() {
    }

    public PostalCode(String postalCode, String city) {
        this.postalCode = postalCode;
        this.city = city;
    }

    // Getters and Setters
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
