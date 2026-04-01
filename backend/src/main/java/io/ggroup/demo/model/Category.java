package io.ggroup.demo.model;

import jakarta.persistence.*;

// Tämä luokka kuvaa tietokannan taulua category
// Taulussa on vain yksi sarake, joka toimii myös taulun pääavaimena
// Taulussa on eri tapahtumien kategoriat esim. music, sport

@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @Column(name = "category", length = 50)
    private String category;

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}