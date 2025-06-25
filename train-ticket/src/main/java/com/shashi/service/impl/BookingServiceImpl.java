package com.shashi.service.impl;

import com.shashi.entity.Booking;
import com.shashi.repository.BookingRepository;
import com.shashi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking findById(String bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<String> getBookedSeats(int trainNo) {
        return bookingRepository.findByTrain_TrainNo(trainNo).stream()
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toList());
    }
}