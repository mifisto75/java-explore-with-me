package ru.practicum.exploreWithMe.adminApi.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.essence.auxiliary.AllRepository;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.AdminStateAction;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.essence.category.mapper.CategoryMapper;
import ru.practicum.exploreWithMe.essence.category.model.Category;
import ru.practicum.exploreWithMe.essence.category.model.QCategory;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.UpdateCompilationRequest;
import ru.practicum.exploreWithMe.essence.compilation.mapper.CompilationMapper;
import ru.practicum.exploreWithMe.essence.compilation.model.Compilation;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventAdminRequest;
import ru.practicum.exploreWithMe.essence.event.mapper.EventMapper;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.event.model.QEvent;
import ru.practicum.exploreWithMe.essence.exeptions.BadRequest;
import ru.practicum.exploreWithMe.essence.exeptions.Conflict;
import ru.practicum.exploreWithMe.essence.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.essence.user.dto.UserDto;
import ru.practicum.exploreWithMe.essence.user.mapper.UserMapper;
import ru.practicum.exploreWithMe.essence.user.model.QUser;
import ru.practicum.exploreWithMe.essence.user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @PersistenceContext
    private EntityManager entityManager;
    private final AllRepository allRepository;

    //API для работы с категориями
    @Transactional
    @Override
    public CategoryDto adminAddCategories(NewCategoryDto newCategoryDto) {
        String name = new JPAQuery<QCategory>(entityManager)
                .select(QCategory.category.name)
                .from(QCategory.category)
                .where(QCategory.category.name.in(newCategoryDto.getName()))
                .fetchFirst();
        if (name != null){
            throw new Conflict("Нарушение целостности данных");
        }
        return CategoryMapper.toCategoryDto(allRepository.categoryRepository.save(CategoryMapper.toCategoryFromNew(newCategoryDto)));
    }

    @Transactional
    @Override
    public void adminDeleteCategories(Long catId) {
        if (allRepository.eventRepository.existsByCategory(allRepository.getCategoryById(catId))) {
            throw new Conflict("Существуют события, связанные с категорией");
        }
        allRepository.categoryRepository.deleteById(catId);
    }

    @Transactional
    @Override
    public CategoryDto adminUpdateCategories(Long catId, NewCategoryDto newCategoryDto) {
        Category category = allRepository.getCategoryById(catId);
        Category name = new JPAQuery<QCategory>(entityManager)
                .select(QCategory.category)
                .from(QCategory.category)
                .where(QCategory.category.name.in(newCategoryDto.getName()))
                .fetchFirst();
        if (name != null){
            if (name.getId() != catId){
                throw new Conflict("Нарушение целостности данных");
            }
        }
        category.setName(newCategoryDto.getName());
            allRepository.categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    //API для работы с событиями
    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> adminGetEvents(
            List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, Integer from, Integer size) {

        List<Event> eventList;

        BooleanExpression whereClause = QEvent.event.isNotNull();

        if (users != null && !users.isEmpty()) {
            whereClause = whereClause.and(QEvent.event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            whereClause = whereClause.and(QEvent.event.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            whereClause = whereClause.and(QEvent.event.category.id.in(categories));
        }
        if (rangeStart != null && rangeEnd != null) {
            whereClause = whereClause.and(QEvent.event.eventDate.between(rangeStart, rangeEnd));
        }

        eventList = new JPAQuery<Event>(entityManager)
                .select(QEvent.event)
                .from(QEvent.event)
                .where(whereClause)
                .orderBy(QEvent.event.eventDate.asc())
                .offset(from)
                .limit(size)
                .fetch();

        return eventList.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto adminUpdateEvents(Long eventId, UpdateEventAdminRequest update) {
        Event event = allRepository.getEventById(eventId);
        if(update.getEventDate() != null){
            if (update.getEventDate().isBefore(LocalDateTime.now())) {
                throw new BadRequest("Запрос составлен некорректно");
            }
        }
        if (event.getState().equals(State.PUBLISHED) || event.getState().equals(State.CANCELED)){
            throw new Conflict("Нарушение целостности данных");
        }
        Optional.ofNullable(update.getAnnotation()).ifPresent(event::setAnnotation);
        if (update.getCategory() != null) {
            event.setCategory(allRepository.getCategoryById(update.getCategory()));
        }
        if (update.getStateAction() != null){
            if (update.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
            } else {
                event.setState(State.CANCELED);
            }
        }
        Optional.ofNullable(update.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(update.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(update.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(update.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(update.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(update.getTitle()).ifPresent(event::setTitle);
        return EventMapper.toEventFullDto(allRepository.eventRepository.save(event));
    }

    //API для работы с пользователями
    @Transactional(readOnly = true)
    @Override
    public List<UserDto> adminGetUsers(List<Long> ids, Integer from, Integer size) {
        List<User> userList;
        BooleanExpression whereClause = QUser.user.isNotNull();
        if (ids != null && !ids.isEmpty()) {
            whereClause = whereClause.and(QUser.user.id.in(ids));
        }
        userList = new JPAQuery<User>(entityManager)
                .select(QUser.user)
                .from(QUser.user)
                .where(whereClause)
                .offset(from)
                .limit(size)
                .fetch();
        return userList
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto adminAddUser(NewUserRequest newUserRequest) {
       String email = new JPAQuery<QUser>(entityManager)
                .select(QUser.user.email)
                .from(QUser.user)
                .where(QUser.user.email.in(newUserRequest.getEmail()))
                .fetchFirst();
        if (email != null){
                throw new Conflict("Нарушение целостности данных");
        }
        return UserMapper.toUserDto(allRepository.userRepository.save(UserMapper.toUserFromNewUser(newUserRequest)));
    }

    @Transactional
    @Override
    public void adminDeleteUser(Long userId) {
        allRepository.getUserById(userId);
        allRepository.userRepository.deleteById(userId);
    }

    //API для работы с подборками событий
    @Transactional
    @Override
    public CompilationDto adminAddCompilations(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilationFromNewCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            compilation.setEvents(newCompilationDto.getEvents()
                    .stream()
                    .map(allRepository::getEventById)
                    .collect(Collectors.toList()));
        }else {
            List<Event>  nul = new ArrayList<>();
            compilation.setEvents(nul);
        }
        if (newCompilationDto.getPinned()==null){
            compilation.setPinned(false);
        }
        return CompilationMapper.toCompilationDto(allRepository.compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public void adminDeleteCompilations(Long compId) {
        allRepository.getCompilationById(compId);
        allRepository.compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public CompilationDto adminUpdateCompilations(Long compId, UpdateCompilationRequest update) {
        Compilation compilation = allRepository.getCompilationById(compId);
        if (update.getEvents() != null && !update.getEvents().isEmpty()) {
            compilation.setEvents(update.getEvents()
                    .stream()
                    .map(allRepository::getEventById)
                    .collect(Collectors.toList()));
        }
        Optional.ofNullable(update.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(update.getPinned()).ifPresent(compilation::setPinned);
        return CompilationMapper.toCompilationDto(allRepository.compilationRepository.save(compilation));
    }
}
