package com.example.zoo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "zoo_user")
public class User {
    @Id
    @GenericGenerator(
            name = "user_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "user_sequence"
    )
    private UUID id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name ="username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name ="phone", nullable = false)
    private String phone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "theme", nullable = false)
    private String theme;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Rating> ratings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<Booking> bookings = new ArrayList<>();
    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String phone, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setEmail(email);
        setPhone(phone);
        setPassword(password);
        setRole("user");
        setTheme("light");
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName= lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password= password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    //getters
    public UUID getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getTheme() {
        return theme;
    }

    public List<Animal> getFavorites() {
        return favorites;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addFavorite(Animal animal) {
        favorites.add(animal);
    }

    public void removeFavorite(Animal animal) {
        favorites.remove(animal);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phone='" + this.getPhone() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", theme='" + this.getTheme() + '\'' +
                '}';
    }


}
