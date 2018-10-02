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
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).stream().forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> caloriesInADay = new HashMap<>();

        for (UserMeal meal:mealList){
            caloriesInADay.put(meal.getDateTime().toLocalDate(), meal.getCalories() + caloriesInADay.getOrDefault(meal.getDateTime().toLocalDate(),0));
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal:mealList){
            if (isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)) {
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesInADay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesInADay = mealList.stream()
                .collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter((meal) -> isBetween(meal.getDateTime().toLocalTime(),startTime,endTime))
                .map((meal) -> new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                        caloriesInADay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ))
                .collect(Collectors.toList());
    }
}
