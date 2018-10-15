package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int mealId){
        log.debug("get for userId = {}, mealId = {}",authUserId(), mealId);
        return service.get(authUserId(), mealId);
    }

    public List<MealWithExceed> getAll(){
        log.debug("getAll for userId = {}",authUserId());
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public Meal create(Meal meal){
        checkNew(meal);
        log.debug("create for userId = {}",authUserId());
        return service.create(authUserId(), meal);
    }

    public void delete(int mealId){
        log.debug("delete for userId = {}, mealId = {}",authUserId(), mealId);
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal,int mealId){
        assureIdConsistent(meal, mealId);
        log.debug("update for userId = {}, mealId = {}",authUserId(), mealId);
        service.update(authUserId(), meal, mealId);
    }

    public List<MealWithExceed> getFiltered(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo){
        log.debug("getFiltered for userId = {}",authUserId());
        return service.getFiltered(authUserId(), authUserCaloriesPerDay(),
                (dateFrom == null)?LocalDate.MIN:dateFrom,
                (dateTo == null)?LocalDate.MAX:dateTo,
                (timeFrom == null)?LocalTime.MIN:timeFrom,
                (timeTo == null)?LocalTime.MAX:timeTo);
    }
}