package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findAllByUserId(Integer id, Sort sort);

    @Override
    @Transactional
    Meal save(Meal s);

    @Transactional
    int deleteByIdAndUserId(Integer id, Integer userId);

    Optional<Meal> findByIdAndUserId(Integer id, Integer userId);

    List<Meal> findByDateTimeBetweenAndUserId(LocalDateTime startDate, LocalDateTime endDate, Integer userId, Sort sort);
}
