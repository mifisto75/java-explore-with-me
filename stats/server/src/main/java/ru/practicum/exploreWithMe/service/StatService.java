package ru.practicum.exploreWithMe.service;

import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.dao.StatsDao;
import ru.practicum.exploreWithMe.exeptions.BadRequest;
import ru.practicum.exploreWithMe.mapper.EndpointHitMapper;
import ru.practicum.exploreWithMe.mapper.ViewStatsMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatService {
    public final StatsDao statsDao;

    public StatService(StatsDao statsDao) {
        this.statsDao = statsDao;
    }

    public List<ViewStatsDto> getState(LocalDateTime start , LocalDateTime end , String ip) {
        if(start.isAfter(end)){
            throw new BadRequest("некоректные значения start и end (start старше чем end)");
        }
        statsDao.getState(start , end , ip);
        return statsDao.getState(start , end , ip)
                .stream()
                .map(ViewStatsMapper::toDto)
                .collect(Collectors.toList());
    }


    public EndpointHitDto addState(EndpointHitDto endpointHitDto) {
        return EndpointHitMapper.toDto(statsDao.addState(EndpointHitMapper.toHit(endpointHitDto)));
    }
}
