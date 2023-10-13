package ru.practicum.exploreWithMe.essence.auxiliary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.essence.category.model.Category;
import ru.practicum.exploreWithMe.essence.category.repository.CategoryRepository;
import ru.practicum.exploreWithMe.essence.compilation.model.Compilation;
import ru.practicum.exploreWithMe.essence.compilation.repository.CompilationRepository;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.event.repository.EventRepository;
import ru.practicum.exploreWithMe.essence.exeptions.NotFound;
import ru.practicum.exploreWithMe.essence.like.repository.LikeRepository;
import ru.practicum.exploreWithMe.essence.request.repository.RequestRepository;
import ru.practicum.exploreWithMe.essence.user.model.User;
import ru.practicum.exploreWithMe.essence.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AllRepository {
    public final CategoryRepository categoryRepository;
    public final EventRepository eventRepository;
    public final UserRepository userRepository;
    public final CompilationRepository compilationRepository;
    public final RequestRepository requestRepository;
    public final LikeRepository likeRepository ;


    public Category getCategoryById(Long catId) {
        return categoryRepository
                .findById(catId).orElseThrow(() -> new NotFound("Категория не найдена или недоступна"));
    }

    public Event getEventById(Long eventId) {
        return eventRepository
                .findById(eventId).orElseThrow(() -> new NotFound("Событие не найдено или недоступно"));
    }

    public User getUserById(Long userId) {
        return userRepository
                .findById(userId).orElseThrow(() -> new NotFound("Пользователь не найден или недоступен"));
    }

    public Compilation getCompilationById(Long compId) {
        return compilationRepository
                .findById(compId).orElseThrow(() -> new NotFound("Подборка не найдена или недоступна"));
    }
}
