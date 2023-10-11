package ru.practicum.exploreWithMe.essence.compilation.dto;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    List<EventShortDto> events;
    long id;
    Boolean pinned;
    String title;

}
