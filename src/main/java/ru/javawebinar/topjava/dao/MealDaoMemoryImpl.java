package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoMemoryImpl implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDaoMemoryImpl.class);

    private Map<Long, Meal> meals = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public List<Meal> getAll() {
        log.debug("getAll");
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal read(Long id) {
        Meal result = meals.get(id);
        log.debug("[READ] for id={} result = {}", id, result);
        return result;
    }

    @Override
    public void delete(Long id) {
        Meal result = meals.remove(id);
        log.debug("[DELETE] for id={} result = {}", id, result);
    }

    @Override
    public Meal create(Meal meal) {
        Long newId = idCounter.incrementAndGet();
        Meal tempMeal = new Meal(newId,meal.getDateTime(),meal.getDescription(),meal.getCalories());
        log.debug("[CREATE] : " + tempMeal);
        meals.put(newId,tempMeal);
        return tempMeal;
    }

    @Override
    public Meal update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
            log.debug("[UPDATE] : {}",meal);
            return meal;
        }else{
            log.debug("[UPDATE] : this record does not exist: {}",meal);
            return null;
        }
    }

}
