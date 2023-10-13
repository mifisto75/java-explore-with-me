package ru.practicum.exploreWithMe.essence.compilation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class NewCompilationDto {
    Set<Long> events;
    Boolean pinned;
    @NotBlank
    @Size(min = 1, max = 50)
    String title;
}
