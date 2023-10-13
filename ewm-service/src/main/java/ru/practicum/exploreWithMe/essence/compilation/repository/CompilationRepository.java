package ru.practicum.exploreWithMe.essence.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.essence.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
