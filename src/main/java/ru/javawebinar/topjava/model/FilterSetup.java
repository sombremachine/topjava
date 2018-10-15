package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterSetup{
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime timeFrom;
    private LocalTime timeTo;

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public FilterSetup(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public FilterSetup() {
        this(null,null,null,null);
    }

    @Override
    public String toString() {
        return "FilterSetup{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", timeFrom=" + timeFrom +
                ", timeTo=" + timeTo +
                '}';
    }
}
