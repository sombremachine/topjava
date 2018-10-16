package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(x -> this.save(1, new Meal(x)));
        MealsUtil.MEALS.forEach(x -> this.save(2, new Meal(x)));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        repository.computeIfAbsent(userId, mealMap -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        return (userMealMap != null) && userMealMap.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        return (userMealMap == null) ? null : userMealMap.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate dateFrom, LocalDate dateTo) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        return (userMealMap == null) ? Collections.emptyList() : userMealMap.values().stream()
                .filter(x -> isBetween(x.getDate(), dateFrom, dateTo))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

