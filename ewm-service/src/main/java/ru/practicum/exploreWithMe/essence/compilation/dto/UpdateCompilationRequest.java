package ru.practicum.exploreWithMe.essence.compilation.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCompilationRequest {
    List<Long> events;

    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;

}
