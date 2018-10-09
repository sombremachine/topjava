package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MealDAOMemoryImpl implements MealDAO {
    private static final Logger log = LoggerFactory.getLogger(MealDAOMemoryImpl.class);

    private Map<Long, Meal> meals = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public List<Meal> getAllMeal() {
        log.debug("getAllMeal");
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getMeal(Long id) {
        Meal result = meals.get(id);
        log.debug("[GET] for id=" + id + " result = " + result);
        return result;
    }

    @Override
    public void deleteMeal(Long id) {
        Meal result = meals.remove(id);
        log.debug("[DELETE] for id=" + id + " result = " + result);
    }

    @Override
    public void addMeal(Meal meal) {
        Long newId = idCounter.incrementAndGet();
        Meal tempMeal = new Meal(newId,meal.getDateTime(),meal.getDescription(),meal.getCalories());
        log.debug("[ADD] : " + tempMeal);
        meals.put(newId,tempMeal);
    }

    @Override
    public void updateMeal(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
            log.debug("[UPDATE] : " + meal);
        }else{
            log.debug("[UPDATE] : this record does not exist" + meal);
        }
    }

}
