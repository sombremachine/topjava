package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoFactory;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static MealDao mealDao = MealDaoFactory.getMealDAO(MealDaoFactory.DAOSources.memory);
    private static final int maxCalories = 2000;

    @Override
    public void init(){
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 1,2), "Завтрак", 500));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 2), "Обед", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 3), "Ужин", 490));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 4), "Завтрак", 1000));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 5), "Обед", 500));
        mealDao.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 6), "Ужин", 510));
    }

    private void logStacktrace(Exception e){
        StringWriter stack = new StringWriter();
        e.printStackTrace(new PrintWriter(stack));
        log.error(stack.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet()");
        String action = request.getParameter("action");
        log.debug("action = " + action);
        try {
            switch (String.valueOf(action).toLowerCase()) {
                case "delete": {
                    log.debug("case delete");
                    Long id = Long.parseLong(request.getParameter("id"));
                    mealDao.delete(id);
                    log.debug("redirect to meals");
                    response.sendRedirect("meals");
                    break;
                }
                case "update": {
                    log.debug("case update");
                    Long id = Long.parseLong(request.getParameter("id"));
                    Meal editMeal = mealDao.read(id);
                    request.setAttribute("editMeal", editMeal);
                }
                default:{
                    log.debug("case default, show list");
                    List<MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, maxCalories);
                    request.setAttribute("meals", filteredWithExceeded);
                    log.debug("forward to meals");
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
                }
            }
        }
        catch (Exception e){
            logStacktrace(e);
            log.debug("redirect to meals");
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("doPost()");
        String action = request.getParameter("action");
        log.debug("action = " + action);
        try {
            String description = request.getParameter("description");
            Integer calories = Integer.parseInt(request.getParameter("calories"));
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
            switch (String.valueOf(action).toLowerCase()) {
                case "create": {
                    log.debug("case create");
                    mealDao.create(new Meal(dateTime, description, calories));
                    break;
                }
                case "update": {
                    log.debug("case update");
                    Long id = Long.parseLong(request.getParameter("id"));
                    mealDao.update(new Meal(id,dateTime, description, calories));
                    break;
                }
            }
        }
        catch (Exception e){
            logStacktrace(e);
        }
        finally {
            log.debug("redirect to meals");
            response.sendRedirect("meals");
        }
    }
}
