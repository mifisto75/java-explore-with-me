package ru.practicum.exploreWithMe.essence.exeptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFound extends IllegalArgumentException {
    public NotFound(String message) {
        super(message);
        log.warn(message);
    }
}
