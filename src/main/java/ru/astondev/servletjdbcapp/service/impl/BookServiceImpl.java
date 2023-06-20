package ru.astondev.servletjdbcapp.service.impl;

import ru.astondev.servletjdbcapp.dao.BookDao;
import ru.astondev.servletjdbcapp.dao.BookDaoImpl;
import ru.astondev.servletjdbcapp.exception.BookNotFoundException;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao = new BookDaoImpl();

    public BookServiceImpl() throws ClassNotFoundException {
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book findById(int id) {
        return bookDao.findBookById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Book update(int id, Book book) {
        return bookDao.update(id,book);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        bookDao.deleteById(id);
    }
    @Override
    public void setStudentForBook(int studentId, int bookId) {
        bookDao.setStudentForBook(studentId, bookId);
    }
    @Override
    public void untieBookFromStudent(int bookId) {
        bookDao.findBookById(bookId).orElseThrow(BookNotFoundException::new);
        bookDao.untieBookFromStudent(bookId);
    }

    @Override
    public void deleteAll() {
        bookDao.deleteAll();
    }
}
