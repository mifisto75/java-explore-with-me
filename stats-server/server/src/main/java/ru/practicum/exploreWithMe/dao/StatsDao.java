package ru.practicum.exploreWithMe.dao;

import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsDao {
    List<ViewStats> getState(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    EndpointHit addState(EndpointHit endpointHit);
}
