package com.shashi.service;

import com.shashi.entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> getAllTrains();
    Train getTrainByNumber(int trainNo);
    void addTrain(Train train);
    void updateTrain(Train train);
    void deleteTrain(int trainNo);
}
