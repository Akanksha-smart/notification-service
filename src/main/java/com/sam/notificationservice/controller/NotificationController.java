package com.sam.notificationservice.controller;

import com.sam.notificationservice.entity.Notification;
import com.sam.notificationservice.repository.NotificationRepository;
import com.sam.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")


public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;


    @GetMapping("/send/{matchId}")
    public void sendManualNotification(@RequestParam Long matchId) {
        notificationService.checkMatchesForNotification(matchId);
    }


    @GetMapping
    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

}
