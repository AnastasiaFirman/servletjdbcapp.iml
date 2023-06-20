package ru.astondev.servletjdbcapp.service;

import ru.astondev.servletjdbcapp.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book findById (int id);
    Book update(int id, Book book);
    List<Book> findAll();
    void deleteById(int id);
    void setStudentForBook(int studentId, int bookId);
    void untieBookFromStudent(int bookId);
    void deleteAll();

}
