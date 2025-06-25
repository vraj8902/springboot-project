package com.shashi.repository;

import com.shashi.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, String> {
}
