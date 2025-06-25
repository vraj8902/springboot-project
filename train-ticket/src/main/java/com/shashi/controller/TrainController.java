package com.shashi.controller;

import com.shashi.entity.Train;
import com.shashi.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainController {

    private final TrainRepository trainRepository;

    // ✅ Show all trains
    @GetMapping("/admin/trains")
    public String getAllTrains(Model model) {
        List<Train> trains = trainRepository.findAll();
        model.addAttribute("trains", trains);
        return "view-trains";
    }

    // ✅ Show add train form
    @GetMapping("/admin/train/add")
    public String showAddTrainForm(Model model) {
        model.addAttribute("train", new Train());
        return "add-train";
    }

    // ✅ Handle add train form submission
    @PostMapping("/admin/train/add")
    public String addTrain(@ModelAttribute Train train) {
        train.setNumberOfCoaches(train.getNumberOfCoaches()); // Auto-sets total & available seats
        trainRepository.save(train);
        return "redirect:/admin/trains";
    }

    // ✅ Show edit train form
    @GetMapping("/admin/train/edit/{trainNo}")
    public String showEditTrainForm(@PathVariable String trainNo, Model model) {
        Train train = trainRepository.findById(trainNo).orElse(null);
        if (train == null) {
            return "redirect:/admin/trains?error=NotFound";
        }
        model.addAttribute("train", train);
        return "edit-train";
    }

    // ✅ Handle edit train form submission
    @PostMapping("/admin/train/edit")
    public String updateTrain(@ModelAttribute Train train) {
        train.setNumberOfCoaches(train.getNumberOfCoaches()); // Recalculate seats
        trainRepository.save(train);
        return "redirect:/admin/trains";
    }

    // ✅ Handle delete train
    @GetMapping("/admin/train/delete/{trainNo}")
    public String deleteTrain(@PathVariable String trainNo) {
        trainRepository.deleteById(trainNo);
        return "redirect:/admin/trains";
    }
}
