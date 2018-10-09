package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {
    List<Meal> getAllMeal();

    Meal getMeal(Long id);
    void deleteMeal(Long id);
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
}
