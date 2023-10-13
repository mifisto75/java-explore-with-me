package ru.practicum.exploreWithMe.essence.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.category.model.Category;
import ru.practicum.exploreWithMe.essence.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategory(Category category);
}
