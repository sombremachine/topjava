package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int mealId){
        return service.get(authUserId(), mealId);
    }

    public List<Meal> getAll(){
        return service.getAll(authUserId());
    }

    public Meal create(Meal meal){
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int mealId){
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal,int mealId){
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal, mealId);
    }


}