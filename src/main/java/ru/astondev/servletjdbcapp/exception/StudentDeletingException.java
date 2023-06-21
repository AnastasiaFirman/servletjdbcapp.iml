package ru.astondev.servletjdbcapp.exception;

public class StudentDeletingException extends RuntimeException {
    public StudentDeletingException() {
        super("Ученик не может быть отчислен, пока не сдаст книги");
    }
}
