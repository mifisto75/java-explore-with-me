package ru.practicum.exploreWithMe.essence.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.like.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
