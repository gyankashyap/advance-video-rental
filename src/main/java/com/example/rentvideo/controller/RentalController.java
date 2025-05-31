package com.example.rentvideo.controller;

import com.example.rentvideo.dto.RentalDto;
import com.example.rentvideo.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalDto>> getUserRentals(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rentalService.getUserRentals(userDetails.getUsername()));
    }

    @PostMapping("/videos/{videoId}/rent")
    public ResponseEntity<RentalDto> rentVideo(@PathVariable Long videoId, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(rentalService.rentVideo(videoId, userDetails.getUsername()), HttpStatus.CREATED);
    }

    @PostMapping("/videos/{videoId}/return")
    public ResponseEntity<RentalDto> returnVideo(@PathVariable Long videoId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rentalService.returnVideo(videoId, userDetails.getUsername()));
    }
}
