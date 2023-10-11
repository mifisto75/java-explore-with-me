package ru.practicum.exploreWithMe.adminApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.adminApi.service.AdminService;
import ru.practicum.exploreWithMe.adminApi.service.AdminServiceImpl;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.UpdateCompilationRequest;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventAdminRequest;
import ru.practicum.exploreWithMe.essence.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.essence.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    //API для работы с категориями
    @PostMapping("/categories")   // Добавление новой категории
    public ResponseEntity<CategoryDto> adminAddCategories(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("метод adminAddCategories");
        return new ResponseEntity<>(adminService.adminAddCategories(newCategoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/{catId}") // Удаление категории
    public ResponseEntity adminDeleteCategories(@PathVariable Long catId) {
        log.info("метод adminDeleteCategories");
        adminService.adminDeleteCategories(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PatchMapping("/categories/{catId}") // Изменение категории
    public ResponseEntity<CategoryDto> adminUpdateCategories(@PathVariable Long catId,
                                                             @Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("метод adminUpdateCategories");
        return new ResponseEntity<>(adminService.adminUpdateCategories(catId, newCategoryDto), HttpStatus.OK);
    }

    //API для работы с событиями
    @GetMapping("/events") // Поиск событий
    public ResponseEntity<List<EventFullDto>> adminGetEvents(
            @RequestParam(required = false) List<Long> users, //  список id пользователей, чьи события нужно найти
            @RequestParam(required = false) List<State> states, //  список состояний в которых находятся искомые события
            @RequestParam(required = false) List<Long> categories, //  список id категорий в которых будет вестись поиск
            @RequestParam(required = false) LocalDateTime rangeStart, //  дата и время не раньше которых должно произойти событие
            @RequestParam(required = false) LocalDateTime rangeEnd,  //  дата и время не позже которых должно произойти событие
            @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.info("метод adminGetEvents");
        return new ResponseEntity<>(adminService.adminGetEvents(
                users, states, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @PatchMapping("/events/{eventId}") // Редактирование данных события и его статуса (отклонение/публикация).
    public ResponseEntity<EventFullDto> adminUpdateEvents(@PathVariable Long eventId,
                                                          @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("метод adminUpdateEvents");
        return new ResponseEntity<>(adminService.adminUpdateEvents(eventId, updateEventAdminRequest), HttpStatus.OK);
    }

    //API для работы с пользователями
    @GetMapping("/users") //  Получение информации о пользователях
    public ResponseEntity<List<UserDto>> adminGetUsers(@RequestParam(required = false) List<Long> ids, // id пользователей
                                                       @RequestParam(defaultValue = "0", required = false) Integer from,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size) {
        log.info("метод adminGetUsers");
        return new ResponseEntity<>(adminService.adminGetUsers(ids, from, size), HttpStatus.OK);
    }

    @PostMapping("/users") // Добавление нового пользователя
    public ResponseEntity<UserDto> adminAddUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("метод adminAddUser");
        return new ResponseEntity<>(adminService.adminAddUser(newUserRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{userId}") // Удаление пользователя
    public ResponseEntity adminDeleteUser(@PathVariable Long userId) {
        log.info("метод adminDeleteUser");
        adminService.adminDeleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //API для работы с подборками событий
    @PostMapping("/compilations") // Добавление новой подборки (подборка может не содержать событий)
    public ResponseEntity<CompilationDto> adminAddCompilations(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("метод adminAddCompilations");
        return new ResponseEntity<>(adminService.adminAddCompilations(newCompilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/compilations/{compId}") // Удаление подборки
    public ResponseEntity adminDeleteCompilations(@PathVariable Long compId) {
        log.info("метод adminDeleteCompilations");
        adminService.adminDeleteCompilations(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> adminUpdateCompilations(
            @PathVariable Long compId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("метод adminUpdateCompilations");
        return new ResponseEntity<>(adminService.adminUpdateCompilations(compId, updateCompilationRequest), HttpStatus.OK);
    }

}