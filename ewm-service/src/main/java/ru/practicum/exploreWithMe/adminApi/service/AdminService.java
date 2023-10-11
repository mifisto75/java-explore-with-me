package ru.practicum.exploreWithMe.adminApi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.UpdateCompilationRequest;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventAdminRequest;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.essence.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
    CategoryDto adminAddCategories(NewCategoryDto newCategoryDto);

    void adminDeleteCategories(Long catId);


    CategoryDto adminUpdateCategories(Long catId, NewCategoryDto newCategoryDto);

    List<EventFullDto> adminGetEvents(
            List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto adminUpdateEvents(Long eventId, UpdateEventAdminRequest update);

    //API для работы с пользователями
    @Transactional
    List<UserDto> adminGetUsers(List<Long> ids, Integer from, Integer size);

    @Transactional
    UserDto adminAddUser(NewUserRequest newUserRequest);

    @Transactional
    void adminDeleteUser(Long userId);

    //API для работы с подборками событий
    @Transactional
    CompilationDto adminAddCompilations(NewCompilationDto newCompilationDto);

    @Transactional
    void adminDeleteCompilations(Long compId);

    @Transactional
    CompilationDto adminUpdateCompilations(Long compId, UpdateCompilationRequest update);
}
