package ru.practicum.exploreWithMe.publicApi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicService {
    @Transactional(readOnly = true)
    List<CompilationDto> publicGetCompilations(Boolean pinned, Integer from, Integer size);

    @Transactional(readOnly = true)
    CompilationDto publicGetCompilationById(Long comId);

    @Transactional(readOnly = true)
    List<CategoryDto> publicGetCategories(Integer from, Integer size);

    @Transactional(readOnly = true)
    CategoryDto publicGetCategoryById(Long catId);

    @Transactional(readOnly = true)
    List<EventShortDto> publicGetEvents(String text, List<Long> categories, Boolean paid,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Boolean onlyAvailable, String sort, int from, int size,
                                        HttpServletRequest request);

    @Transactional(readOnly = true)
    EventFullDto publicGetEventById(Long id, HttpServletRequest request);
}
