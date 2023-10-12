package ru.practicum.exploreWithMe.publicApi.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.StatsClient;
import ru.practicum.exploreWithMe.ViewStatsDto;
import ru.practicum.exploreWithMe.essence.auxiliary.AllRepository;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.category.mapper.CategoryMapper;
import ru.practicum.exploreWithMe.essence.category.model.Category;
import ru.practicum.exploreWithMe.essence.category.model.QCategory;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.mapper.CompilationMapper;
import ru.practicum.exploreWithMe.essence.compilation.model.Compilation;
import ru.practicum.exploreWithMe.essence.compilation.model.QCompilation;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.essence.event.mapper.EventMapper;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.event.model.QEvent;
import ru.practicum.exploreWithMe.essence.exeptions.BadRequest;
import ru.practicum.exploreWithMe.essence.exeptions.NotFound;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {
    @PersistenceContext
    private EntityManager entityManager;

    private final AllRepository allRepository;
    private final StatsClient statsClient;

    @Transactional(readOnly = true)
    @Override
    public List<CompilationDto> publicGetCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> dtoList;
        if (pinned != null) {
            dtoList = new JPAQuery<Compilation>(entityManager)
                    .select(QCompilation.compilation)
                    .from(QCompilation.compilation)
                    .where(QCompilation.compilation.pinned.in(pinned))
                    .offset(from)
                    .limit(size)
                    .fetch();
        } else {
            dtoList = new JPAQuery<Compilation>(entityManager)
                    .select(QCompilation.compilation)
                    .from(QCompilation.compilation)
                    .offset(from)
                    .limit(size)
                    .fetch();
        }
        return dtoList
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CompilationDto publicGetCompilationById(Long comId) {
        return CompilationMapper.toCompilationDto(allRepository.getCompilationById(comId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> publicGetCategories(Integer from, Integer size) {
        List<CategoryDto> dtoList = new JPAQuery<Category>(entityManager)
                .select(QCategory.category)
                .from(QCategory.category)
                .offset(from)
                .limit(size)
                .fetch()
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto publicGetCategoryById(Long catId) {
        return CategoryMapper.toCategoryDto(allRepository.getCategoryById(catId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> publicGetEvents(String text, List<Long> categories, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               Boolean onlyAvailable, String sort, int from, int size,
                                               HttpServletRequest request) {

        List<Event> eventList = null;

        BooleanExpression whereClause = QEvent.event.isNotNull();

        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new BadRequest("некорректно указана дата rangeEnd");
            }
            whereClause = whereClause.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
        } else if (rangeStart == null && rangeEnd == null) {
            whereClause = whereClause.and(QEvent.event.eventDate.after(LocalDateTime.now()));
        }

        if (text != null && !text.isBlank()) {
            whereClause = whereClause.and(QEvent.event.annotation.containsIgnoreCase(text)
                    .or(QEvent.event.description.containsIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            whereClause = whereClause.and(QEvent.event.category.id.in(categories));
        }
        if (paid != null) {
            whereClause = whereClause.and(QEvent.event.paid.eq(paid));
        }


        if (onlyAvailable != null) {
            if (onlyAvailable) {
                whereClause = whereClause
                        .and(QEvent.event.participantLimit.eq(0)
                                .or(QEvent.event.participantLimit.goe(QEvent.event.confirmedRequests)));
            }
        }
        if (sort != null) {
            if (sort.equalsIgnoreCase("EVENT_DATE")) {
                eventList = new JPAQuery<Event>(entityManager)
                        .select(QEvent.event)
                        .from(QEvent.event)
                        .where(whereClause)
                        .orderBy(QEvent.event.eventDate.asc())
                        .offset(from)
                        .limit(size)
                        .fetch();
                addHit(request);

            } else if (sort.equalsIgnoreCase("VIEWS")) {
                eventList = new JPAQuery<Event>(entityManager)
                        .select(QEvent.event)
                        .from(QEvent.event)
                        .where(whereClause)
                        .orderBy(QEvent.event.views.asc())
                        .offset(from)
                        .limit(size)
                        .fetch();
                addHit(request);
            }
        } else {
            eventList = new JPAQuery<Event>(entityManager)
                    .select(QEvent.event)
                    .from(QEvent.event)
                    .where(whereClause)
                    .offset(from)
                    .limit(size)
                    .fetch();
            addHit(request);
        }
        return eventList
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto publicGetEventById(Long id, HttpServletRequest request) {
        Event event = allRepository.getEventById(id);
        if (event.getState() != State.PUBLISHED) {
            throw new NotFound("Событие не найдено или недоступно");
        }
        addHit(request);
        event.setViews(getHit(request));
        allRepository.eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    private void addHit(HttpServletRequest request) {
        EndpointHitDto dto = new EndpointHitDto();
        dto.setApp("ewm-service");
        dto.setUri(request.getRequestURI());
        dto.setIp(request.getRemoteAddr());
        dto.setTimestamp(LocalDateTime.now());
        statsClient.addState(dto);
    }

    private Long getHit(HttpServletRequest request) {
        List<ViewStatsDto> dtoList = statsClient.getHit("2020-10-11 03:48:05", "2099-10-11 03:48:05", request.getRequestURI(), true).getBody();
        Long hit = dtoList.get(0).getHits();
        return hit;
    }

}
