package com.example.zoo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GenericGenerator(
            name = "image_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "image_sequence"
    )
    private UUID id;
    @Column(name = "title",
            nullable = false)
    private String title;
    @Column(name = "location",
            nullable = false,
            unique = true)
    private String location;
    @Column(name = "type",
            nullable = false)
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;

    public Image() {
    }

    public Image(String title, String location, String type) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.location = location;
        this.type = type;
    }

    //setters

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    //getters

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Image{" +
                ", title='" + getTitle() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", type='" + getType() + '\'' +
                '}';
    }
}
