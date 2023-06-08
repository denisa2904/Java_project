package com.example.zoo.api;

import com.example.zoo.model.Booking;
import com.example.zoo.model.User;
import com.example.zoo.service.BookingService;
import com.example.zoo.service.JwtService;
import com.example.zoo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/users/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final JwtService jwtService;

    public BookingController(BookingService bookingService, JwtService jwtService, UserService userService) {
        this.bookingService = bookingService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping
    public List<Booking> getBookings(@NonNull HttpServletRequest request){
        System.out.println("GET BOOKINGS");
        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        UUID userId = userService.getUserByUsername(username).getId();
        System.out.println("am ajuns aici");
        return bookingService.getBookingsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody Booking booking, @NonNull HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        User user = userService.getUserByUsername(username);
        booking.setUser(user);
        LocalDate date = LocalDate.now();
        booking.setDate(date);
        user.addBooking(booking);
        if(bookingService.addBooking(booking)==1)
            return ResponseEntity.status(201).body("CREATED");
        return ResponseEntity.status(400).body("BAD REQUEST");
    }
}
