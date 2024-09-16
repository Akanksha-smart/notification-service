package com.sam.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="notification_entity")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String recipient;
    private LocalDateTime scheduledTime;
    private boolean seen = false;


    public  NotificationEntity(String message, LocalDateTime scheduledTime) {
        this.message = message;
        this.scheduledTime = scheduledTime;
    }
}
