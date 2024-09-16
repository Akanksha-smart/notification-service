package com.sam.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

    private Long id;
    private LocalDateTime startTime;  // Match start time
    private String coachEmail;
    private String teamA;
    private String teamB;
}
