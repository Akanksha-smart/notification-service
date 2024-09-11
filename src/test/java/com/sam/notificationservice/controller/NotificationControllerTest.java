package com.sam.notificationservice.controller;

import com.sam.notificationservice.entity.NotificationEntity;
import com.sam.notificationservice.exception.MatchNotFoundException;
import com.sam.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void testSendManualNotification_Success() throws Exception {
        doNothing().when(notificationService).checkMatchesForNotification(anyLong());

        mockMvc.perform(get("/api/notifications/send/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully for match ID: 1"));

        verify(notificationService, times(1)).checkMatchesForNotification(1L);
    }

    @Test
    void testSendManualNotification_MatchNotFound() throws Exception {
        doThrow(new MatchNotFoundException("Match not found")).when(notificationService).checkMatchesForNotification(anyLong());

        mockMvc.perform(get("/api/notifications/send/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Match not found with ID: 1"));

        verify(notificationService, times(1)).checkMatchesForNotification(1L);
    }

    @Test
    void testSendManualNotification_ServerError() throws Exception {
        doThrow(new RuntimeException("Some error")).when(notificationService).checkMatchesForNotification(anyLong());

        mockMvc.perform(get("/api/notifications/send/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while sending the notification: Some error"));

        verify(notificationService, times(1)).checkMatchesForNotification(1L);
    }
}
