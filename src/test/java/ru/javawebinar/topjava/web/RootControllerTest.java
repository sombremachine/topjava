package ru.javawebinar.topjava.web;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {
    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    void selectUser(User user) throws Exception {
        mockMvc.perform(post("/users")
                .param("userId", String.valueOf(user.getId())))
                .andExpect(status().isFound());
    }

    void testUserMeal(User user, List<Meal> meals) throws Exception {
        selectUser(user);
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(meals.size())))
                .andExpect(model().attribute("meals", new BaseMatcher<MealTo>() {
                    @Override
                    public void describeTo(Description description) {

                    }

                    @Override
                    public boolean matches(Object o) {
                        Set<Integer> expected = new HashSet<>();
                        meals.forEach(x -> expected.add(x.getId()));
                        if (o instanceof Collection) {
                            Set<Integer> actual = new HashSet<>();
                            ((Collection<MealTo>) o).forEach(x -> actual.add(x.getId()));
                            return actual.containsAll(expected) && (actual.size() == expected.size());
                        } else {
                            return expected.contains(((MealTo) o).getId());
                        }
                    }
                }));

    }

    @Test
    void testMealsUser() throws Exception {
        testUserMeal(USER, MEALS);
    }

    @Test
    void testMealsAdmin() throws Exception {
        testUserMeal(ADMIN, Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2));
    }
}