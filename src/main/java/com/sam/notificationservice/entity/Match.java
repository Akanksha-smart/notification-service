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
@Table(name="cricket_matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchId")
    private Long matchId;

    @Column(name = "teamA")
    private String teamA;

    @Column(name = "teamB")
    private String teamB;

    @Column(name = "matchDateTime")
    private LocalDateTime matchDateTime;

    @Column(name = "location")
    private String location;

    @Column(name = "matchType")
    private String matchType;

    @Column(name = "matchStage")
    private String matchStage;

    @Column(name = "matchGroup")
    private String matchGroup;

    @Column(name = "live")
    private Boolean live;

    @Column(name = "matchStatus")
    private String matchStatus;

}
