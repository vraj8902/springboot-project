package com.shashi.service;

import com.shashi.entity.Booking;

public interface BookingService {
    Booking findById(String bookingId);
    void updateBooking(Booking booking);
}
