package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {
    List<Meal> getAll();

    Meal create(Meal meal);
    Meal read(Long id);
    Meal update(Meal meal);
    void delete(Long id);
}
