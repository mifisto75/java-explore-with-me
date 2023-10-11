package ru.practicum.exploreWithMe.essence.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.request.model.ParticipationRequest;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
}
