package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = MealAjaxController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealAjaxController extends AbstractMealController{
    static final String REST_URL = "/ajax/meals";
//    @Override
//    @GetMapping("/{id}")
//    public Meal get(@PathVariable("id") int id) {
//        return super.get(id);
//    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

//    @Override
//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
//        super.update(meal, id);
//    }
//
    @PostMapping
    public void createWithLocation(@RequestParam("id") Integer id,
                                   @RequestParam("dateTime") String dateTime,
                                   @RequestParam("description") String description,
                                   @RequestParam("calories") Integer calories) {
        Meal meal = new Meal(id,LocalDateTime.parse(dateTime.replace(' ','T')),description,calories);

        if (meal.isNew()) {
            super.create(meal);
        }
    }
//
//    @Override
//    @GetMapping(value = "/filter")
//    public List<MealTo> getBetween(
//            @RequestParam(value = "startDate", required = false) LocalDate startDate,
//            @RequestParam(value = "startTime", required = false) LocalTime startTime,
//            @RequestParam(value = "endDate", required = false) LocalDate endDate,
//            @RequestParam(value = "endTime", required = false) LocalTime endTime) {
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }
}
