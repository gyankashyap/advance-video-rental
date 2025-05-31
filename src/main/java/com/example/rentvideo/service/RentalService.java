package com.example.rentvideo.service;

import com.example.rentvideo.dto.RentalDto;
import com.example.rentvideo.exception.BadRequestException;
import com.example.rentvideo.exception.ResourceNotFoundException;
import com.example.rentvideo.model.Rental;
import com.example.rentvideo.model.User;
import com.example.rentvideo.model.Video;
import com.example.rentvideo.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final VideoService videoService;

    public RentalService(RentalRepository rentalRepository, UserService userService, VideoService videoService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.videoService = videoService;
    }

    public List<RentalDto> getUserRentals(String email) {
        User user = userService.findByEmail(email);
        return rentalRepository.findByUserAndActiveTrue(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RentalDto rentVideo(Long videoId, String email) {
        User user = userService.findByEmail(email);
        Video video = videoService.findById(videoId);
        
        // Check if user already has 2 active rentals
        int activeRentals = rentalRepository.countByUserIdAndActiveTrue(user.getId());
        if (activeRentals >= 2) {
            throw new BadRequestException("You cannot rent more than 2 videos at a time");
        }
        
        // Check if the video is available
        if (!video.isAvailable()) {
            throw new BadRequestException("Video is not available for rent");
        }
        
        // Create rental
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVideo(video);
        rental.setRentDate(LocalDateTime.now());
        rental.setActive(true);
        
        // Update video availability
        video.setAvailable(false);
        
        Rental savedRental = rentalRepository.save(rental);
        return convertToDto(savedRental);
    }

    @Transactional
    public RentalDto returnVideo(Long videoId, String email) {
        User user = userService.findByEmail(email);
        
        // Find active rental for this user and video
        Rental rental = rentalRepository.findByVideoIdAndUserIdAndActiveTrue(videoId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active rental found for this video"));
        
        // Update rental
        rental.setReturnDate(LocalDateTime.now());
        rental.setActive(false);
        
        // Update video availability
        Video video = rental.getVideo();
        video.setAvailable(true);
        
        Rental updatedRental = rentalRepository.save(rental);
        return convertToDto(updatedRental);
    }

    private RentalDto convertToDto(Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .userId(rental.getUser().getId())
                .videoId(rental.getVideo().getId())
                .videoTitle(rental.getVideo().getTitle())
                .rentDate(rental.getRentDate())
                .returnDate(rental.getReturnDate())
                .active(rental.isActive())
                .build();
    }
}
