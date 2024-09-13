package com.sam.notificationservice.controller;

import com.sam.notificationservice.entity.Notification;
import com.sam.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/unseen/{matchId}")
    public List<Notification> getUnseenNotifications(@PathVariable Long matchId) {
        return notificationService.getUnseenNotifications(String.valueOf(matchId));
    }

    @PostMapping("/seen/{id}")
    public void markNotificationAsSeen(@PathVariable Long id) {
        notificationService.markNotificationAsSeen(id);
    }

}



