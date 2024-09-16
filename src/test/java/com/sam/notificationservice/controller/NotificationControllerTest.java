package com.sam.notificationservice.controller;


import com.sam.notificationservice.entity.Notification;
import com.sam.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }


    @Test
    void testGetNotifications() throws Exception {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setMessage("Match 1 notification");

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setMessage("Match 2 notification");

        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationRepository.findAll()).thenReturn(notifications);

        mockMvc.perform(get("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'message':'Match 1 notification'},{'id':2,'message':'Match 2 notification'}]"));

        verify(notificationRepository, times(1)).findAll();
    }

}
