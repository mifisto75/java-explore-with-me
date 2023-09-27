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
    public List<ViewStats> getState(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris.get(0).equals("NULL")) {
                return statsRepository.nullUrisUniqueTrue(start, end);
            }
            return statsRepository.uniqueTrue(start, end, uris);
        } else {
            if (uris.get(0).equals("NULL")) {
                return statsRepository.nullUrisUniqueFalse(start, end);
            }
            return statsRepository.uniqueFalse(start, end, uris);
        }
    }

    @Override
    public EndpointHit addState(EndpointHit endpointHit) {
        return statsRepository.save(endpointHit);
    }
}
