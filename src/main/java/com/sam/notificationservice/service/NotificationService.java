package com.sam.notificationservice.service;


import com.sam.notificationservice.entity.Match;
import com.sam.notificationservice.entity.NotificationEntity;
import com.sam.notificationservice.repository.MatchRepository;
import com.sam.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

    @Service
    public class NotificationService {

        @Autowired
        private NotificationRepository notificationRepository;

        @Autowired
        private MatchRepository matchRepository;

        public void saveNotification(String message) {
            NotificationEntity notification = new NotificationEntity(message, LocalDateTime.now());
            notificationRepository.save(notification);
        }

        public void sendNotification(String message) {
            saveNotification(message);
        }

        public void checkMatchesForNotification(Long matchId) {
            List<Match> matches = matchRepository.findByMatchId(matchId);

            LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            for (Match match : matches) {
                LocalDateTime matchStartTime = match.getMatchDateTime().truncatedTo(ChronoUnit.MINUTES);
                LocalDateTime notificationTime = matchStartTime.minusMinutes(15);

                if (currentTime.isEqual(notificationTime)) {
                    sendNotification("Match between " + match.getTeamA() + " and " + match.getTeamB() + " starts in 15 minutes!");
                }
            }
        }

}


