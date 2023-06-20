package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.dbutils.BookSqlQueries;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.exception.BookNotFoundException;
import ru.astondev.servletjdbcapp.exception.SqlProcessingException;
import ru.astondev.servletjdbcapp.model.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public BookDaoImpl() throws ClassNotFoundException {
    }

    @Override
    public Optional<Book> findBookById(int bookId) {
        Book book = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.FIND_BOOK_BY_ID)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = new Book();
                book.setId(bookId);
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Book save(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.SAVE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.FIND_ALL_BOOKS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = Book.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .author(resultSet.getString("author"))
                        .build();
                books.add(book);
            }
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
        return books;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.DELETE_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public Book update(int id, Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.UPDATE_BOOK_BY_ID)) {
            findBookById(id);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return book;
    }

    @Override
    public void setStudentForBook(int studentId, int bookId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.SET_STUDENT_FOR_BOOK)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public void untieBookFromStudent(int bookId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.SET_BOOK_FOR_STUDENT_TO_NULL)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(BookSqlQueries.DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }
}
