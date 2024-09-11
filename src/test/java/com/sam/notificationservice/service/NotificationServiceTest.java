package com.sam.notificationservice.service;

import com.sam.notificationservice.entity.Match;

import java.util.Collections;
import java.util.List;


import com.sam.notificationservice.entity.Match;
import com.sam.notificationservice.entity.NotificationEntity;
import com.sam.notificationservice.repository.MatchRepository;
import com.sam.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNotification() {
        // Arrange
        String message = "Test notification";
        NotificationEntity notification = new NotificationEntity(message, LocalDateTime.now());

        // Act
        notificationService.saveNotification(message);

        // Assert
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testSendNotification() {
        // Arrange
        String message = "Test notification";

        // Act
        notificationService.sendNotification(message);

        // Assert
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testCheckMatchesForNotification_WhenNotificationIsTriggered() {
        // Arrange
        Long matchId = 1L;
        Match match = new Match();
        match.setMatchId(matchId);
        match.setTeamA("Team A");
        match.setTeamB("Team B");
        match.setMatchDateTime(LocalDateTime.now().plusMinutes(15)); // Match starts in 15 minutes

        List<Match> matches = Collections.singletonList(match);

        when(matchRepository.findByMatchId(matchId)).thenReturn(matches);

        // Act
        notificationService.checkMatchesForNotification(matchId);

        // Assert
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testCheckMatchesForNotification_WhenNoNotificationIsTriggered() {
        // Arrange
        Long matchId = 1L;
        Match match = new Match();
        match.setMatchId(matchId);
        match.setTeamA("Team A");
        match.setTeamB("Team B");
        match.setMatchDateTime(LocalDateTime.now().plusHours(1)); // Match starts in 1 hour

        List<Match> matches = Collections.singletonList(match);

        when(matchRepository.findByMatchId(matchId)).thenReturn(matches);

        // Act
        notificationService.checkMatchesForNotification(matchId);

        // Assert
        verify(notificationRepository, never()).save(any(NotificationEntity.class));
    }
}


