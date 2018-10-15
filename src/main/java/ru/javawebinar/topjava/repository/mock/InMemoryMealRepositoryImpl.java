package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(x -> this.save(1,x));
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
        return repository.getOrDefault(userId,Collections.emptyMap()).remove(mealId) != null;

    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).values().stream().sorted((f1,f2) -> f2.getDateTime().compareTo(f1.getDateTime())).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate dateFrom, LocalDate dateTo) {
        return getAll(userId).stream().filter(x -> isBetween(x.getDate(),dateFrom,dateFrom)).collect(Collectors.toList());
    }
}

