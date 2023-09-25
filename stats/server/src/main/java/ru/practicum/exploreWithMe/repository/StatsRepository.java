package ru.practicum.exploreWithMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {




//смотрит стотистику отдельного юзера по IP
    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri, COUNT(hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "AND hit.ip IN ?3 " +
            "GROUP BY hit.uri, hit.ip " +
            "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> all(LocalDateTime start, LocalDateTime end, String ip);

    //смотрит сумарную статистику по всем эндоинтам
    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(hit.app, hit.uri,COUNT(hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE hit.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> OneTimes(LocalDateTime start, LocalDateTime end);

}
