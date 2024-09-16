package com.sam.notificationservice.service;

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
        String message = "Test notification message";

        notificationService.saveNotification(message);

        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testSendNotification() {
        String message = "Match notification message";

        notificationService.sendNotification(message);

        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
    }

    @Test
    void testCheckMatchesForNotification_NoUpcomingMatches() {
        Long matchId = 1L;

        when(matchRepository.findByMatchId(matchId)).thenReturn(Collections.emptyList());

        notificationService.checkMatchesForNotification();

        verify(notificationRepository, times(0)).save(any(NotificationEntity.class));
    }

    @Test
    void testCheckMatchesForNotification_MatchNotIn15Minutes() {
        Long matchId = 1L;
        Match match = new Match();
        match.setMatchId(matchId);
        match.setTeamA("Team A");
        match.setTeamB("Team B");
        match.setMatchDateTime(LocalDateTime.now().plusHours(1));

        List<Match> matches = Collections.singletonList(match);

        when(matchRepository.findByMatchId(matchId)).thenReturn(matches);
        notificationService.checkMatchesForNotification();

        verify(notificationRepository, times(0)).save(any(NotificationEntity.class));
    }

}
