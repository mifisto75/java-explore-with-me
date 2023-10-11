package ru.practicum.exploreWithMe.essence.compilation.dto;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class UpdateCompilationRequest {
    List<Long> events;

    Boolean pinned;
    @Size(min = 1,max = 50)
    String title;

}
