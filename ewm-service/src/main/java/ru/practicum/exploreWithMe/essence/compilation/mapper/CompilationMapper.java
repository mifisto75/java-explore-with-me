package ru.practicum.exploreWithMe.essence.compilation.mapper;

import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.model.Compilation;
import ru.practicum.exploreWithMe.essence.event.mapper.EventMapper;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setEvents(compilation.getEvents()
                .stream()
                .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toList()));
        dto.setPinned(compilation.getPinned());
        dto.setTitle(compilation.getTitle());
        return dto;
    }
    public static Compilation toCompilationFromNewCompilation(NewCompilationDto dto) {
     Compilation compilation = new Compilation();
     compilation.setPinned(dto.getPinned());
     compilation.setTitle(dto.getTitle());
     return compilation;
    }
}
