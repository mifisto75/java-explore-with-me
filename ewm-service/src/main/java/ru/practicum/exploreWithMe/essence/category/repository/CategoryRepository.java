package ru.practicum.exploreWithMe.essence.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}