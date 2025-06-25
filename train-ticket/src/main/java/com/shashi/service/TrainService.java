package com.shashi.service;

import com.shashi.entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> getAllTrains();
    Train getTrainByNumber(String trainNo);
    void addTrain(Train train);
    void updateTrain(Train train);
    void deleteTrain(String trainNo);
}
