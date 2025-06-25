package com.shashi.service;

import com.shashi.entity.Booking;
import java.util.List;

public interface BookingService {
    Booking findById(String bookingId);
    void updateBooking(Booking booking);
    List<String> getBookedSeats(int trainNo); // ✅ method to fetch booked seat numbers
}