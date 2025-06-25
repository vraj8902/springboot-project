package com.shashi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Train {

    @Id
    private String trainNo;

    private String trainName;

    private String source;

    private String destination;

    private double fare;

    private int totalSeats;

    private int availableSeats;

    private String departureTime;

    private String arrivalTime;

    private String travelDate;

    private int numberOfCoaches; // ✅ New field

    public int getNumberOfCoaches() {
    return numberOfCoaches;
}

public void setNumberOfCoaches(int numberOfCoaches) {
    System.out.println("Setting numberOfCoaches: " + numberOfCoaches);
    this.numberOfCoaches = numberOfCoaches;
    this.totalSeats = numberOfCoaches * 60;
    this.availableSeats = this.totalSeats;
}
}
