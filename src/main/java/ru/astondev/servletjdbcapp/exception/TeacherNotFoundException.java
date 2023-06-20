package ru.astondev.servletjdbcapp.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException() {
        super("Преподаватель с таким id не найден");
    }
}
