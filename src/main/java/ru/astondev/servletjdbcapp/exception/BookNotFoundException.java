package ru.astondev.servletjdbcapp.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Книга с таким id не найдена");
    }
}
