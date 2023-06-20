package ru.astondev.servletjdbcapp.exception;

public class SqlProcessingException extends RuntimeException{
    public SqlProcessingException(Throwable e) {
        super(e);
    }
}
