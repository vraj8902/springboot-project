package com.shashi.controller;

import com.shashi.entity.Booking;
import com.shashi.entity.Train;
import com.shashi.entity.User;
import com.shashi.repository.BookingRepository;
import com.shashi.repository.TrainRepository;
import com.shashi.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SpringTemplateEngine templateEngine;

    @GetMapping("/download-ticket/{bookingId}")
    public void downloadTicketPdf(@PathVariable String bookingId, HttpServletResponse response) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        Context context = new Context();
        context.setVariable("bookingId", booking.getBookingId());
        context.setVariable("user", booking.getUser());
        context.setVariable("train", booking.getTrain());
        context.setVariable("paid", booking.isPaid());
        context.setVariable("fare", booking.getFare());
        context.setVariable("seatsBooked", booking.getSeatsBooked());
        context.setVariable("seatNumbers", booking.getSeatNumbers());
        context.setVariable("bookingTime", booking.getBookingTime());

        String htmlContent = templateEngine.process("ticket-pdf", context);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(htmlContent, null);
        builder.toStream(baos);
        builder.run();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
    }

    @GetMapping("/book/{trainNo}")
    public String showBookingPage(@PathVariable String trainNo, Model model) {
        Train train = trainRepository.findById(trainNo).orElse(null);
        if (train == null || train.getAvailableSeats() <= 0) {
            return "redirect:/home?error=NoSeats";
        }

        List<String> bookedSeats = bookingRepository.findByTrain(train).stream()
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toList());

        model.addAttribute("train", train);
        model.addAttribute("bookedSeats", bookedSeats);
        model.addAttribute("numberOfCoaches", train.getNumberOfCoaches()); // ensure coach count available
        return "booking";
    }

    @GetMapping("/ticket-success")
    public String ticketSuccess(@RequestParam String bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "ticket-success";
    }

    @PostMapping("/book/{trainNo}")
    public String bookTrain(@PathVariable String trainNo,
                            @RequestParam("selectedSeats") List<String> selectedSeats,
                            Authentication authentication,
                            Model model) {

        Train train = trainRepository.findById(trainNo).orElse(null);
        if (train == null) {
            return "redirect:/home?error=TrainNotFound";
        }

        if (selectedSeats == null || selectedSeats.isEmpty() || selectedSeats.size() > 4) {
            return "redirect:/home?error=InvalidSeatSelection";
        }

        List<String> alreadyBookedSeats = bookingRepository.findByTrain(train).stream()
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toList());

        for (String seat : selectedSeats) {
            if (alreadyBookedSeats.contains(seat)) {
                return "redirect:/home?error=SeatAlreadyBooked";
            }
        }

        if (train.getAvailableSeats() < selectedSeats.size()) {
            return "redirect:/home?error=InsufficientSeats";
        }

        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login-page";
        }

        User user = optionalUser.get();
        double totalFare = train.getFare() * selectedSeats.size();

        Booking booking = Booking.builder()
                .bookingId(UUID.randomUUID().toString())
                .user(user)
                .train(train)
                .seatsBooked(selectedSeats.size())
                .seatNumbers(selectedSeats)
                .paid(false)
                .bookingTime(LocalDateTime.now())
                .fare(totalFare)
                .build();

        bookingRepository.save(booking);

        train.setAvailableSeats(train.getAvailableSeats() - selectedSeats.size());
        trainRepository.save(train);

        model.addAttribute("bookingId", booking.getBookingId());
        model.addAttribute("amount", totalFare);
        return "payment";
    }
}
