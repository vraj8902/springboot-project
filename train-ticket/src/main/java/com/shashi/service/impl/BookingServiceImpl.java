package com.shashi.service.impl;

import com.shashi.entity.Booking;
import com.shashi.repository.BookingRepository;
import com.shashi.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking findById(String bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
