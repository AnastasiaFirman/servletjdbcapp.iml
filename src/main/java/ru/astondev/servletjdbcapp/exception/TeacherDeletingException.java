package ru.astondev.servletjdbcapp.exception;

public class TeacherDeletingException extends  RuntimeException{
    public TeacherDeletingException() {
        super("Перед увольнением необходимо передать студентов другому преподавателю");
    }
}
