package ru.astondev.servletjdbcapp.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException() {
        super("Студент с таким id не найден");
    }
}
