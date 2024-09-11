package com.sam.notificationservice.controller;

import com.sam.notificationservice.entity.NotificationEntity;
import com.sam.notificationservice.exception.MatchNotFoundException;
import com.sam.notificationservice.repository.NotificationRepository;
import com.sam.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    // Endpoint to manually trigger notifications based on the tournament ID
    @GetMapping("/send/{matchId}")
    public ResponseEntity<String> sendManualNotification(@PathVariable Long matchId) {
        try {
            notificationService.checkMatchesForNotification(matchId);
            return ResponseEntity.ok("Notification sent successfully for match ID: " + matchId);
        } catch (MatchNotFoundException e) {
            // Custom exception for when the match is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Match not found with ID: " + matchId);
        } catch (Exception e) {
            // General exception handling for any other issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sending the notification: " + e.getMessage());
        }
    }


    @GetMapping
    public List<NotificationEntity> getNotifications() {
        return notificationRepository.findAll(); // Or any other logic to fetch notifications
    }

}
