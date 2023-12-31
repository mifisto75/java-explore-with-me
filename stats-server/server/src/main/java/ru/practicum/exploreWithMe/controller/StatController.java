package ru.practicum.exploreWithMe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatController {

    private final StatService statService;


    public StatController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto saveStat(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("метод saveStat endpointHitDto = " + endpointHitDto);
        return statService.addState(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,

            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,

            @RequestParam(required = false, defaultValue = "NULL") List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("метод getStats start = " + start + " , end = " + end + " uris = " + uris + " unique = " + unique);
        return statService.getState(start, end, uris, unique);
    }
}

