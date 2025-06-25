package com.shashi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    private String bookingId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Train train;

    private int seatsBooked;

    private double fare;

    private boolean paid;

    private LocalDateTime bookingTime;

    @ElementCollection
    private List<String> seatNumbers; // ✅ List of seat IDs like "1-5B", "2-9A", etc.
}
