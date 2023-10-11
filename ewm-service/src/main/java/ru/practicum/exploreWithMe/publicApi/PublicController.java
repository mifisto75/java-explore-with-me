package ru.practicum.exploreWithMe.publicApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.privateApi.service.PrivateService;
import ru.practicum.exploreWithMe.publicApi.service.PublicService;
import ru.practicum.exploreWithMe.publicApi.service.PublicServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class PublicController {
    private PublicService publicService;
    @Autowired
    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    // Публичный API для работы с подборками событий
    @GetMapping("/compilations")
    public ResponseEntity<List<CompilationDto>> publicGetCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.info("метод publicGetCompilations ");
        return new ResponseEntity<>(publicService.publicGetCompilations(pinned, from, size), HttpStatus.OK);
    }

    @GetMapping("/compilations/{comId}")
    public ResponseEntity<CompilationDto> publicGetCompilationById(@PathVariable Long comId) {
        log.info("метод publicGetCompilationById ");
        return new ResponseEntity<>(publicService.publicGetCompilationById(comId), HttpStatus.OK);
    }

    // Публичный API для работы с категориями
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> publicGetCategories(
            @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.info("метод publicGetCategories ");
        return new ResponseEntity<>(publicService.publicGetCategories(from, size), HttpStatus.OK);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> publicGetCategoryById(@PathVariable Long catId) {
        log.info("метод publicGetCategoryById ");
        return new ResponseEntity<>(publicService.publicGetCategoryById(catId), HttpStatus.OK);
    }

    // Публичный API для работы с событиями
    @GetMapping("/events")
    public ResponseEntity<List<EventShortDto>> publicGetEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE") String sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            HttpServletRequest request) {
        log.info("метод publicGetEvents ");
return new ResponseEntity<>(publicService.publicGetEvents(text,categories,paid,rangeStart,rangeEnd
        ,onlyAvailable,sort,from,size,request),HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    public  ResponseEntity<EventFullDto> publicGetEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("метод publicGetEventById ");
        return new ResponseEntity<>(publicService.publicGetEventById(id,request),HttpStatus.OK);

    }


}
