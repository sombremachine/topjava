package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
//@Sql(scripts = {"classpath:db/initDB.sql","classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.JANUARY, 22, 12, 0, 29), "meal_admin", 500);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(admin_meal_12_22_12, admin_meal_9_22_23, newMeal));
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() {
        service.create(new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 22, 23, 0, 29), "meal_duplicate_admin", 1200), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(admin_meal_9_22_23_id, ADMIN_ID);
        assertMatch(meal, admin_meal_9_22_23);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() {
        Meal meal = service.get(admin_meal_9_22_23_id, USER_ID);
        assertMatch(meal, admin_meal_9_22_23);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(admin_meal_9_22_23_id, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), admin_meal_12_22_12);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUser() {
        service.delete(admin_meal_9_22_23_id, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test
    public void getBetweenDatesOpen() {
        List<Meal> actual = service.getBetweenDates(LocalDate.of(2018, Month.OCTOBER, 1), LocalDate.of(2018, Month.DECEMBER, 31), USER_ID);
        assertMatch(actual, Arrays.asList(user_meal_11_22_23, user_meal_11_22_12));
    }

    @Test
    public void getBetweenDateClosed() {
        List<Meal> actual = service.getBetweenDates(LocalDate.of(2018, Month.AUGUST, 22), LocalDate.of(2018, Month.AUGUST, 22), USER_ID);
        assertMatch(actual, Arrays.asList(user_meal_8_22_23, user_meal_8_22_12));
    }

    @Test
    public void getBetweenDateTimesOpen() {
        List<Meal> actual = service.getBetweenDateTimes(
                LocalDateTime.of(2018, Month.NOVEMBER, 22, 20, 0, 29),
                LocalDateTime.of(2018, Month.NOVEMBER, 22, 23, 30, 29),
                USER_ID);
        assertMatch(actual, user_meal_11_22_23);
    }

    @Test
    public void getBetweenDateTimesOpenExtended() {
        List<Meal> actual = service.getBetweenDateTimes(
                LocalDateTime.of(2018, Month.NOVEMBER, 21, 20, 0, 29),
                LocalDateTime.of(2018, Month.NOVEMBER, 23, 23, 30, 29),
                USER_ID);
        assertMatch(actual, Arrays.asList(user_meal_11_22_23, user_meal_11_22_12));
    }

    @Test
    public void getBetweenDateTimesClose() {
        List<Meal> actual = service.getBetweenDateTimes(
                LocalDateTime.of(2018, Month.AUGUST, 22, 12, 0, 29),
                LocalDateTime.of(2018, Month.AUGUST, 22, 12, 0, 29),
                USER_ID);
        assertMatch(actual, user_meal_8_22_12);
    }

    @Test
    public void getAllAdmin() {
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(admin_meal_12_22_12, admin_meal_9_22_23));
    }

    @Test
    public void getAllUser() {
        assertMatch(service.getAll(USER_ID), Arrays.asList(user_meal_11_22_23, user_meal_11_22_12, user_meal_8_22_23, user_meal_8_22_12));
    }

    @Test
    public void getAllNotFound() {
        Assert.assertEquals(0, service.getAll(1).size());
    }

    @Test
    public void update() {
        Meal updated = new Meal(admin_meal_9_22_23);
        updated.setDescription("meal_updated_admin");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(admin_meal_9_22_23_id, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUser() {
        Meal updated = new Meal(admin_meal_9_22_23);
        updated.setDescription("meal_updated_admin");
        updated.setCalories(330);
        service.update(updated, USER_ID);
    }
}