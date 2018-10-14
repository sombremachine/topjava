package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void delete(int userId, int mealId) {
        repository.computeIfPresent(userId,(uid, mealMap) ->{
            mealMap.remove(mealId);
            return mealMap.isEmpty() ? null : mealMap;
        });
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).values();
    }
}

