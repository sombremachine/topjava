package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOFactory;
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
    private static MealDAO mealDAO = MealDAOFactory.getMealDAO(MealDAOFactory.DAOSources.memory);


    @Override
    public void init() throws ServletException {
        super.init();
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 1,2), "Завтрак", 500));
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 2), "Обед", 1000));
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 3), "Ужин", 490));
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 4), "Завтрак", 1000));
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 5), "Обед", 500));
        mealDAO.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 6), "Ужин", 510));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet()");
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
            log.error(stack.toString());
        }

        String action = request.getParameter("action");
        log.debug("action = " + action);
        try {
            switch (String.valueOf(action).toLowerCase()) {
                case "update": {
                    log.debug("case update");
                    Long id = Long.parseLong(request.getParameter("id"));
                    Meal editMeal = mealDAO.getMeal(id);
                    request.setAttribute("editMeal", editMeal);
                }
                default:{
                    log.debug("case default, show list");
                    List<MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, maxCalories);
                    request.setAttribute("meals", filteredWithExceeded);
                    log.debug("forward to meals");
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
                }
                case "delete": {
                    log.debug("case delete");
                    Long id = Long.parseLong(request.getParameter("id"));
                    mealDAO.deleteMeal(id);
                    log.debug("redirect to meals");
                    response.sendRedirect("meals");
                    break;
                }

            }
        }
        catch (Exception e){
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            log.error(stack.toString());
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
                    mealDAO.addMeal(new Meal(dateTime, description, calories));
                    break;
                }
                case "update": {
                    Long id = Long.parseLong(request.getParameter("id"));
                    mealDAO.updateMeal(new Meal(id,dateTime, description, calories));
                    break;
                }
            }
        }
        catch (Exception e){
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            log.error(stack.toString());
        }
        finally {
            log.debug("redirect to meals");
            response.sendRedirect("meals");
        }
    }
}
