package ru.practicum.exploreWithMe.essence.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewCategoryDto {
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}
