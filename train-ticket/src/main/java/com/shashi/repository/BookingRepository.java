package com.shashi.repository;

import com.shashi.entity.Booking;
import com.shashi.entity.Train;
import com.shashi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    // Option 1: Find by User object
    List<Booking> findByUser(User user);

     List<Booking> findByTrain(Train train);
    // Option 2: Find by username property of User
    List<Booking> findByUser_Username(String username);
}