package com.example.zoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GenericGenerator(
            name = "booking_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "booking_sequence"
    )
    private UUID id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "student_tickets")
    private int studentTickets;

    @Column(name = "adult_tickets")
    private int adultTickets;

    @Column(name = "child_tickets")
    private int childTickets;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Booking() {
    }

    public Booking(int studentTickets, int adultTickets, int childTickets) {
        this.studentTickets = studentTickets;
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStudentTickets() {
        return studentTickets;
    }

    public void setStudentTickets(int studentTickets) {
        this.studentTickets = studentTickets;
    }

    public int getAdultTickets() {
        return adultTickets;
    }

    public void setAdultTickets(int adultTickets) {
        this.adultTickets = adultTickets;
    }

    public int getChildTickets() {
        return childTickets;
    }

    public void setChildTickets(int childTickets) {
        this.childTickets = childTickets;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + getId() +
                ", date='" + getDate() + '\'' +
                ", user=" + getUser().getId() +
                ", studentTickets=" + getStudentTickets() +
                ", adultTickets=" + getAdultTickets() +
                ", childTickets=" + getChildTickets() +
                '}';
    }
}
