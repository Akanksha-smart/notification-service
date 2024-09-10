package com.sam.notificationservice.repository;

import com.sam.notificationservice.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByMatchId(Long matchId);
}
