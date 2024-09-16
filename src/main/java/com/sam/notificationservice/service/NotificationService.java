package com.sam.notificationservice.service;

import com.sam.notificationservice.entity.Match;
import com.sam.notificationservice.entity.NotificationEntity;
import com.sam.notificationservice.repository.MatchRepository;
import com.sam.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

//        public void sendNotification(String message) {
//            saveNotification(message);
//        }

//        public void checkMatchesForNotification(Long matchId) {
//            List<Match> matches = matchRepository.findByMatchId(matchId);
//
//            LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//
//            for (Match match : matches) {
//                LocalDateTime matchStartTime = match.getMatchDateTime().truncatedTo(ChronoUnit.MINUTES);
//                LocalDateTime notificationTime = matchStartTime.minusMinutes(30);
//
//                if (currentTime.isEqual(notificationTime)) {
//                    sendNotification("Match between " + match.getTeamA() + " and " + match.getTeamB() + " starts in 15 minutes!");
//                }
//            }
//        }


//        public String checkMatchesForNotification(Long matchId) {
//            List<Match> matches = matchRepository.findByMatchId(matchId);
//
//            LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//            System.out.println("Current Time: " + currentTime);
//
//            for (Match match : matches) {
//                LocalDateTime matchStartTime = match.getMatchDateTime().truncatedTo(ChronoUnit.MINUTES);
//                LocalDateTime notificationTime = matchStartTime.minusMinutes(15); // Corrected this line
//
//                System.out.println("Match Start Time: " + matchStartTime);
//                System.out.println("Notification Time: " + notificationTime);
//
//                // Check if the current time is equal to the notification time
//                if (currentTime.isEqual(notificationTime)) {
//                    String str = "Match between " + match.getTeamA() + " and " + match.getTeamB() + " starts in 15 minutes!";
//                    System.out.println(str);
//                    sendNotification(str); // Make sure to call sendNotification() to actually send the notification
//                    return str;
//                }
//            }
//            return "No notification found";
//        }


        public String sendNotification(String message) {
            // For testing purposes, return the notification message
            System.out.println("Notification sent: " + message);
            return message;
        }

        public String checkMatchesForNotification() {
            List<Match> matches = matchRepository.findAll();

            LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            System.out.println("Current Time: " + currentTime);

            StringBuilder notifications = new StringBuilder();

            for (Match match : matches) {
                LocalDateTime matchStartTime = match.getMatchDateTime().truncatedTo(ChronoUnit.MINUTES);
                LocalDateTime notificationTime = matchStartTime.minusMinutes(15); // Notification time is 15 minutes before the match start time

                System.out.println("Match Start Time: " + matchStartTime);
                System.out.println("Notification Time: " + notificationTime);

                // Check if the current time is within the range of 15 minutes before the match start time
                if (currentTime.isAfter(notificationTime) && currentTime.isBefore(notificationTime.plusMinutes(15))) {
                    String notificationMessage = "Match between " + match.getTeamA() + " and " + match.getTeamB() + " starts in 15 minutes!";
                    System.out.println(notificationMessage);
                    notifications.append(sendNotification(notificationMessage)).append("\n");
                }
            }

            if (notifications.length() == 0) {
                return "No notifications found";
            }

            return notifications.toString();
        }


        @Scheduled(cron = "0 * * * * *") // Every minute
        public void checkForUpcomingMatches() {
            checkMatchesForNotification();
        }



    }


