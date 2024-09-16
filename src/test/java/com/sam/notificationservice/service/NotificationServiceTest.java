package com.sam.notificationservice.service;

import java.util.Arrays;
import java.util.List;

import com.sam.notificationservice.dto.MatchDTO;
import com.sam.notificationservice.entity.Notification;
import com.sam.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;


    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNotification() {
        String message = "Match starting soon!";
        Long matchId = 1L;

        // Act
        notificationService.saveNotification(message);

        // Assert
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendNotification() {
        String message = "Match starting soon!";
        Long matchId = 1L;

        // Act
        notificationService.sendNotification(message);

        // Assert
        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(messagingTemplate, times(1)).convertAndSendToUser(String.valueOf(eq(Optional.of(Optional.of(matchId)))), eq("/topic/notifications"), eq(message));
    }

    @Test
    void testCheckMatchesForNotification() {
        Long tournamentId = 1L;

        MatchDTO match = new MatchDTO();
        match.setStartTime(LocalDateTime.now().plusMinutes(15));
        match.setCoachEmail("coach@example.com");

//        when(matchClient.getMatchesByTournamentId(tournamentId)).thenReturn(Arrays.asList(match));

        // Act
        notificationService.checkMatchesForNotification();

        // Assert
        verify(messagingTemplate, times(1)).convertAndSendToUser(eq("coach@example.com"), eq("/topic/notifications"), anyString());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetUnseenNotifications() {
        String recipientEmail = "coach@example.com";

        Notification notification1 = new Notification();
        notification1.setSeen(false);

        Notification notification2 = new Notification();
        notification2.setSeen(false);

        when(notificationRepository.findByRecipientEmailAndSeenFalse(recipientEmail)).thenReturn(Arrays.asList(notification1, notification2));

        // Act
        List<Notification> unseenNotifications = notificationService.getUnseenNotifications(recipientEmail);

        // Assert
        assertEquals(2, unseenNotifications.size());
        verify(notificationRepository, times(1)).findByRecipientEmailAndSeenFalse(recipientEmail);
    }

    @Test
    void testMarkNotificationAsSeen() {
        Long notificationId = 1L;

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setSeen(false);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // Act
        notificationService.markNotificationAsSeen(notificationId);

        // Assert
        assertEquals(true, notification.isSeen());
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testMarkNotificationAsSeen_NotFound() {
        Long notificationId = 1L;

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // Act
        notificationService.markNotificationAsSeen(notificationId);

        // Assert
        verify(notificationRepository, never()).save(any(Notification.class));
    }

}


