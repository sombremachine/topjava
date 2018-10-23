package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public static final Meal user_meal_8_22_12;
    public static final Meal user_meal_8_22_23;
    public static final Meal user_meal_11_22_12;
    public static final Meal user_meal_11_22_23;

    public static final Meal admin_meal_9_22_23;
    public static final Meal admin_meal_12_22_12;

    public static final int admin_meal_9_22_23_id;

    static {
        int meal_id = ADMIN_ID;
        user_meal_8_22_12 = new Meal(++meal_id, LocalDateTime.of(2018, Month.AUGUST, 22, 12, 0, 29), "meal_user1", 500);
        user_meal_8_22_23 = new Meal(++meal_id, LocalDateTime.of(2018, Month.AUGUST, 22, 23, 0, 29), "meal_user2", 100);
        user_meal_11_22_12 = new Meal(++meal_id, LocalDateTime.of(2018, Month.NOVEMBER, 22, 12, 0, 29), "meal_user3", 800);
        user_meal_11_22_23 = new Meal(++meal_id, LocalDateTime.of(2018, Month.NOVEMBER, 22, 23, 0, 29), "meal_user4", 400);

        admin_meal_9_22_23 = new Meal(++meal_id, LocalDateTime.of(2018, Month.SEPTEMBER, 22, 23, 0, 29), "meal_admin1", 200);
        admin_meal_9_22_23_id = meal_id;
        admin_meal_12_22_12 = new Meal(++meal_id, LocalDateTime.of(2018, Month.DECEMBER, 22, 12, 0, 29), "meal_admin2", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
