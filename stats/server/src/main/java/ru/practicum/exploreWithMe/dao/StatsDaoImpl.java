package ru.practicum.exploreWithMe.dao;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;
import ru.practicum.exploreWithMe.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatsDaoImpl implements StatsDao {
    private final StatsRepository statsRepository;

    public StatsDaoImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<ViewStats> getState(LocalDateTime start , LocalDateTime end , String ip) {
     if(ip != null){
         return  statsRepository.all(start,end,ip);
     }else{
         return   statsRepository.OneTimes(start,end);
     }
    }

    @Override
    public EndpointHit addState(EndpointHit endpointHit) {
        return statsRepository.save(endpointHit);
    }
}
