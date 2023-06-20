package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.dbutils.StudentSqlQueries;
import ru.astondev.servletjdbcapp.exception.SqlProcessingException;
import ru.astondev.servletjdbcapp.exception.StudentDeletingException;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {
    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public StudentDaoImpl() throws ClassNotFoundException {
    }

    @Override
    public Student save(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.SAVE_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                student.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return student;
    }

    @Override
    public Optional<Student> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.FIND_STUDENT_BY_ID_WITH_BOOKS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Student student = null;
            List<Book> books = new LinkedList<>();
            while (resultSet.next()) {
                if (student == null) {
                    student = createStudent(id, resultSet);
                    student.setBooks(books);
                }
                int bookId = resultSet.getInt("book_id");
                if (bookId != 0) {
                    Book book = createBook(bookId, resultSet);
                    books.add(book);
                }
            }
            return Optional.ofNullable(student);
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.FIND_ALL_STUDENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = Student.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .build();
                students.add(student);
            }
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
        return students;
    }

    @Override
    public Student update(int id, Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.UPDATE_STUDENT_BY_ID)) {
            findById(id);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return student;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.DELETE_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StudentDeletingException();
        }
    }

    @Override
    public void untieStudentFromTeacher(int studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.SET_STUDENT_FOR_TEACHER_TO_NULL)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSqlQueries.DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    private Student createStudent(int studentId, ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        return student;
    }

    private Book createBook(int bookId, ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        return book;
    }

}
