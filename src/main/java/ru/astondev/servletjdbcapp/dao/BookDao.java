package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> findBookById(int bookId);

    Book save(Book book);

    List<Book> findAll();

    void deleteById(int id);

    Book update(int id, Book book);

    void setStudentForBook(int studentId, int bookId);

    void untieBookFromStudent(int bookId);

    void deleteAll();
}
