package com.sam.notificationservice.service;

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
import static org.mockito.Mockito.*;

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

        notificationService.saveNotification(message);

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendNotification() {
        String message = "Match starting soon!";
        Long matchId = 1L;

        notificationService.sendNotification(message);

        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(messagingTemplate, times(1)).convertAndSendToUser(String.valueOf(eq(Optional.of(Optional.of(matchId)))), eq("/topic/notifications"), eq(message));
    }

    @Test
    void testCheckMatchesForNotification() {
        Long matchId = 1L;

        MatchDTO match = new MatchDTO();
        match.setStartTime(LocalDateTime.now().plusMinutes(15));
        match.setCoachEmail("coach@example.com");

        notificationService.checkMatchesForNotification(match.getId());

        verify(messagingTemplate, times(1)).convertAndSendToUser(eq("coach@example.com"), eq("/topic/notifications"), anyString());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

}


