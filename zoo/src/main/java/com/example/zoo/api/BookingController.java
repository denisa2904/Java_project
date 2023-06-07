package com.example.zoo.api;

import com.example.zoo.model.Booking;
import com.example.zoo.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/users/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings() {
        //how do I get userId from the token?
        return bookingService.getBookingsByUserId(null);
    }

    @PostMapping
    public int addBooking(Booking booking) {
        booking.setUser(null);
        //how do I get userId from the token?
        return bookingService.addBooking(booking);
    }
}
