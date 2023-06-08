package com.example.zoo.service;

import com.example.zoo.model.Booking;
import com.example.zoo.repository.BookingRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    public int addBooking(Booking booking) {
        bookingRepo.save(booking);
        return 1;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<Booking> getBookingsByUserId(UUID userId) {
        return bookingRepo.findAllByUserId(userId);
    }
}
