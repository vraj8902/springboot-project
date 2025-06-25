package com.shashi.repository;

import com.shashi.entity.Booking;
import com.shashi.entity.Train;
import com.shashi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByUser(User user);

    List<Booking> findByTrain(Train train);

    List<Booking> findByUser_Username(String username);

    List<Booking> findByTrain_TrainNo(int trainNo); // ✅ added for filtering
}