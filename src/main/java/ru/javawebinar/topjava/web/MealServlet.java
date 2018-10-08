package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final List<Meal> testMeal = new ArrayList<>();


    @Override
    public void init() throws ServletException {
        super.init();
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 1,2), "Завтрак", 500));
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 2), "Обед", 1000));
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 3), "Ужин", 490));
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 4), "Завтрак", 1000));
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 5), "Обед", 500));
        testMeal.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 6), "Ужин", 510));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        Integer maxCalories = 2000;  //не сказано откуда брать
        try{
            String parameterMax = request.getParameter("max");
            if (parameterMax != null) {
                maxCalories = Integer.parseInt(parameterMax);
                log.debug("parameter max = " + maxCalories);
            }
        }
        catch (Exception e){
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            log.debug(stack.toString());
        }
        List<MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(testMeal, LocalTime.MIN, LocalTime.MAX, maxCalories);
        request.setAttribute("meals", filteredWithExceeded);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
