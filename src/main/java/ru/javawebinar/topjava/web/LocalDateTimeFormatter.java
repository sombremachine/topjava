package ru.javawebinar.topjava.web;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.util.Arrays.asList;


public class LocalDateTimeFormatter implements AnnotationFormatterFactory<CustomLocalDateTime>{
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(asList(LocalDateTime.class, LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomLocalDateTime annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(CustomLocalDateTime annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<?> configureFormatterFrom(CustomLocalDateTime annotation, Class<?> fieldType) {
        if (fieldType == LocalDate.class){
            return new MyLocalDateFormatter();
        }else if (fieldType == LocalTime.class){
            return new MyLocalTimeFormatter();
        }
        return new MyLocalLocalDateTimeFormatter();
    }

    private class MyLocalDateFormatter implements Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.toString();
        }
    }

    private class MyLocalTimeFormatter implements Formatter<LocalTime> {
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object.toString();
        }
    }

    private class MyLocalLocalDateTimeFormatter implements Formatter<LocalDateTime> {
        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime object, Locale locale) {
            System.err.println("!!!!!!!!!! "+ object);
            return object.toString();
        }
    }
}
