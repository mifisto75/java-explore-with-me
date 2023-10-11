package ru.practicum.exploreWithMe.essence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}