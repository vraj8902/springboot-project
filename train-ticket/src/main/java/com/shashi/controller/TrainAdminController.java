package com.shashi.controller;

import com.shashi.entity.Train;
import com.shashi.service.TrainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/train")
@RequiredArgsConstructor
public class TrainAdminController {

    private final TrainService trainService;

    @GetMapping("/add")
    public String showAddTrainForm(Model model) {
        model.addAttribute("train", new Train());
        return "add-train";
    }

    @PostMapping("/add")
    public String addTrain(@ModelAttribute Train train) {
        // Each coach has 60 seats; compute total available seats
        int totalSeats = train.getNumberOfCoaches() * 60;
        train.setAvailableSeats(totalSeats);
        trainService.addTrain(train);
        return "redirect:/admin/train/list";
    }

    @GetMapping("/list")
    public String listTrains(Model model) {
        List<Train> trains = trainService.getAllTrains();
        model.addAttribute("trains", trains);
        return "view-trains";
    }

    @GetMapping("/edit/{trainNo}")
    public String showEditForm(@PathVariable String trainNo, Model model) {
        Train train = trainService.getTrainByNumber(trainNo);
        model.addAttribute("train", train);
        return "edit-train";
    }

    @PostMapping("/edit")
    public String editTrain(@ModelAttribute Train train) {
        // Recalculate available seats based on coach count
        int totalSeats = train.getNumberOfCoaches() * 60;
        train.setAvailableSeats(totalSeats);
        trainService.updateTrain(train);
        return "redirect:/admin/train/list";
    }

    @GetMapping("/delete/{trainNo}")
    public String deleteTrain(@PathVariable String trainNo) {
        trainService.deleteTrain(trainNo);
        return "redirect:/admin/train/list";
    }
}
