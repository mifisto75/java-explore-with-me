package ru.practicum.exploreWithMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {


    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "AND hit.uri IN ?3 OR ?3 is NULL " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(DISTINCT hit.ip) DESC")
    List<ViewStats> uniqueTrue(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri,COUNT(hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "AND hit.uri IN ?3 OR ?3 is NULL " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> uniqueFalse(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(DISTINCT hit.ip) DESC")
    List<ViewStats> nullUrisUniqueTrue(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri,COUNT(hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> nullUrisUniqueFalse(LocalDateTime start, LocalDateTime end);
}
