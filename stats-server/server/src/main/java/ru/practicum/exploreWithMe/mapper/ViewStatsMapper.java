package ru.practicum.exploreWithMe.mapper;

import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.model.ViewStats;

public class ViewStatsMapper {
    public static ViewStats toStats(ViewStatsDto dto) {
        ViewStats stats = new ViewStats();
        stats.setApp(dto.getApp());
        stats.setUri(dto.getUri());
        stats.setHits(dto.getHits());
        return stats;
    }

    public static ViewStatsDto toDto(ViewStats stats) {
        ViewStatsDto dto = new ViewStatsDto();
        dto.setApp(stats.getApp());
        dto.setUri(stats.getUri());
        dto.setHits(stats.getHits());
        return dto;
    }
}
