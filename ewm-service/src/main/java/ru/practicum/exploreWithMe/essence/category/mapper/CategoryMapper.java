package ru.practicum.exploreWithMe.essence.category.mapper;

import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.category.dto.NewCategoryDto;
import ru.practicum.exploreWithMe.essence.category.model.Category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;

    }

    public static Category toCategory(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
    public static Category toCategoryFromNew(NewCategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
}
