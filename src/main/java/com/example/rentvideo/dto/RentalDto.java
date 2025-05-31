package com.example.rentvideo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    
    private Long id;
    private Long userId;
    private Long videoId;
    private String videoTitle;
    private LocalDateTime rentDate;
    private LocalDateTime returnDate;
    private boolean active;
}
