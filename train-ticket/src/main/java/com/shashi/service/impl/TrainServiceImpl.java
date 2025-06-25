package com.shashi.service.impl;

import com.shashi.entity.Train;
import com.shashi.repository.TrainRepository;
import com.shashi.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train getTrainByNumber(String trainNo) {
        return trainRepository.findById(trainNo).orElse(null);
    }

    @Override
    public void addTrain(Train train) {
        // Initially, availableSeats == totalSeats
        train.setAvailableSeats(train.getTotalSeats());
        trainRepository.save(train);
    }

    @Override
    public void updateTrain(Train train) {
        Optional<Train> existingOpt = trainRepository.findById(train.getTrainNo());
        if (existingOpt.isPresent()) {
            Train existing = existingOpt.get();
            existing.setTrainName(train.getTrainName());
            existing.setSource(train.getSource());
            existing.setDestination(train.getDestination());
            existing.setFare(train.getFare());

            int oldTotal = existing.getTotalSeats();
            int oldAvailable = existing.getAvailableSeats();
            int newTotal = train.getTotalSeats();

            // Adjust availableSeats proportionally
            int newAvailable = oldAvailable + (newTotal - oldTotal);
            existing.setTotalSeats(newTotal);
            existing.setAvailableSeats(Math.max(newAvailable, 0));

            trainRepository.save(existing);
        }
    }

    @Override
    public void deleteTrain(String trainNo) {
        trainRepository.deleteById(trainNo);
    }
}
