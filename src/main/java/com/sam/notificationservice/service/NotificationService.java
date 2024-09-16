package com.sam.notificationservice.service;

import com.sam.notificationservice.dto.MatchDTO;
import com.sam.notificationservice.entity.Notification;
import com.sam.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
   private NotificationRepository notificationRepository;

    @Autowired
   private MatchClient matchClient;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


        // Save the notification to the database
        public void saveNotification(String message, Long matchId) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setScheduledTime(LocalDateTime.now());
            notification.setSeen(false);  // Mark as unseen initially
            notificationRepository.save(notification);
        }

        // Send a WebSocket message and save the notification in the database
        public void sendNotification(String message, Long matchid) {
            saveNotification(message, matchid);  // Save to DB
            messagingTemplate.convertAndSendToUser(String.valueOf(matchid), "/topic/notifications", message);  // Send WebSocket
        }


        public void checkMatchesForNotification() {
            Long tournamentId = 1L;  // Replace with the actual tournament ID
            List<MatchDTO> matches = matchClient.getMatchesByTournamentId(tournamentId);

            for (MatchDTO match : matches) {
                LocalDateTime matchStartTime = match.getStartTime();
                LocalDateTime currentTime = LocalDateTime.now();

                // Notify 15 minutes before the match starts
                if (currentTime.isBefore(matchStartTime) && currentTime.plusMinutes(15).isAfter(matchStartTime)) {
                    sendNotification("Your match starts in 15 minutes!",match.getId());
                }
            }
        }

        // Get unseen notifications for a particular user (for a notification dashboard)
        public List<Notification> getUnseenNotifications(String recipientEmail) {
            return notificationRepository.findByRecipientEmailAndSeenFalse(recipientEmail);
        }

        // Mark notification as seen
        public void markNotificationAsSeen(Long notificationId) {
            Notification notification = notificationRepository.findById(notificationId).orElse(null);
            if (notification != null) {
                notification.setSeen(true);
                notificationRepository.save(notification);
            }
        }


}
