package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.FilterSetup;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.web.SecurityUtil.setUserId;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
        log.debug(appCtx.toString());
        mealRestController.getAll().forEach(x -> log.debug(x.toString()));
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "save":
                String id = request.getParameter("id");
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")));

                if (meal.isNew()) {
                    log.info("Create {}", meal);
                    mealRestController.create(meal);
                } else {
                    log.info("Update {}", meal);
                    mealRestController.update(meal, meal.getId());
                }
                response.sendRedirect("meals");
                break;
            case "filter":
                this.doGet(request,response);
                break;
            case "all":
            default:
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                LocalDate dateFrom = ((request.getParameter("dateFrom") == null) ||
                        request.getParameter("dateFrom").isEmpty())?
                        null:LocalDate.parse(request.getParameter("dateFrom"));
                LocalDate dateTo = ((request.getParameter("dateTo") == null) ||
                        request.getParameter("dateTo").isEmpty())?
                        null:LocalDate.parse(request.getParameter("dateTo"));
                LocalTime timeFrom = ((request.getParameter("timeFrom") == null) ||
                        request.getParameter("timeFrom").isEmpty())?
                        null:LocalTime.parse(request.getParameter("timeFrom"));
                LocalTime timeTo = ((request.getParameter("timeTo") == null) ||
                        request.getParameter("timeTo").isEmpty())?
                        null:LocalTime.parse(request.getParameter("timeTo"));
                request.setAttribute("dateFrom",dateFrom);
                request.setAttribute("dateTo",dateTo);
                request.setAttribute("timeFrom",timeFrom);
                request.setAttribute("timeTo",timeTo);
                request.setAttribute("meals",
                        mealRestController.getFiltered(dateFrom, dateTo,
                                timeFrom, timeTo));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
