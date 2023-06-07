package com.example.zoo.repository;

import com.example.zoo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepo extends JpaRepository<Booking, UUID> {

    List<Booking> findAllByUserId(UUID userId);
}
