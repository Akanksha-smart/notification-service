package com.sam.notificationservice.repository;

import com.sam.notificationservice.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByMatchId(Long matchId);

    @Query("SELECT m.matchId FROM Match m WHERE m.matchDateTime BETWEEN :start AND :end")
    List<Long> findMatchIdsWithinTimeRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
