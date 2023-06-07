package com.example.zoo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GenericGenerator(
            name = "animal_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "animal_sequence"
    )
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "binomial_name", nullable = false)
    private String binomialName;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "climate", nullable = false)
    private String climate;
    @Column(name = "conservation", nullable = false)
    private String conservation;
    @Column(name = "origin", nullable = false)
    private String origin;
    @Column(name = "description",
            columnDefinition = "TEXT",
            nullable = false)
    private String description;
    @Column(name = "rating", nullable = false)
    private float rating;
    @Column(name = "min_weight", nullable = false)
    private int minWeight;
    @Column(name = "max_weight", nullable = false)
    private int maxWeight;

    public Animal() {
    }

    public Animal(String name, String binomialName, String type, String climate, String conservation, String origin, String description, float rating, int minWeight, int maxWeight) {
        this.name = name;
        this.binomialName = binomialName;
        this.type = type;
        this.climate = climate;
        this.conservation = conservation;
        this.origin = origin;
        this.description = description;
        this.rating = rating;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    //setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBinomialName(String binomialName) {
        this.binomialName = binomialName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    //getters

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBinomialName() {
        return binomialName;
    }

    public String getType() {
        return type;
    }

    public String getClimate() {
        return climate;
    }

    public String getConservation() {
        return conservation;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }



    @Override
    public String toString() {
        return "Animal{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", binomialName='" + getBinomialName() + '\'' +
                ", type='" + getType() + '\'' +
                ", climate='" + getClimate() + '\'' +
                ", conservation='" + getConservation() + '\'' +
                ", origin='" + getOrigin() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", rating=" + getRating() +
                ", minWeight=" + getMinWeight() +
                ", maxWeight=" + getMaxWeight() +
                '}';
    }



}
