package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<Meal> getAll(int userId);

    List<MealWithExceed> getFiltered(int userId, int calories, LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo);

    Meal get(int userId, int mealId);

    Meal create(int userId, Meal meal);

    void delete(int userId, int mealId);

    void update(int userId, Meal meal, int mealId);
}
