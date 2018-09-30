package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).stream().forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> result = new ArrayList<>();
        List<UserMeal> filteredUserMeal = new ArrayList<>();
        Map<LocalDate,Integer> caloriesInADay = new HashMap<>();


        for (UserMeal meal:mealList){
            caloriesInADay.put(toLocalDate(meal.getDateTime()), meal.getCalories() + caloriesInADay.getOrDefault(toLocalDate(meal.getDateTime()),0));
            if (isBetween(toLocalTime(meal.getDateTime()),startTime,endTime)){
                filteredUserMeal.add(meal);
            }
        }

        for (UserMeal meal:filteredUserMeal){
            result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                    caloriesInADay.get(toLocalDate(meal.getDateTime())) > caloriesPerDay ));
        }

        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> result;
        List<UserMeal> filteredUserMeal;
        Map<LocalDate,Integer> caloriesInADay = new HashMap<>();

        filteredUserMeal = mealList.stream()
                .peek((meal)->caloriesInADay.put(toLocalDate(meal.getDateTime()),
                        meal.getCalories() + caloriesInADay.getOrDefault(toLocalDate(meal.getDateTime()),0)))
                .filter((meal) -> isBetween(toLocalTime(meal.getDateTime()),startTime,endTime))
                .collect(Collectors.toList());

        result = filteredUserMeal.stream()
                .map((meal) -> new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                        caloriesInADay.get(toLocalDate(meal.getDateTime())) > caloriesPerDay ))
                .collect(Collectors.toList());

        return result;
    }
}
