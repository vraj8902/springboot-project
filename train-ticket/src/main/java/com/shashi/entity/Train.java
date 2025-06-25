package com.shashi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Train {

    @Id
    private int trainNo;

    private String trainName;

    private String source;

    private String destination;

    private int numberOfCoaches;  // ✅ required field

    private int totalSeats;       // ✅ computed field: numberOfCoaches * 60

    private double fare;

    private int availableSeats;
}
