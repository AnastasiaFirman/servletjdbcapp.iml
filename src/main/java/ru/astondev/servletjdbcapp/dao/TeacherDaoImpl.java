package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.dbutils.StudentSqlQueries;
import ru.astondev.servletjdbcapp.dbutils.TeacherSqlQueries;
import ru.astondev.servletjdbcapp.exception.SqlProcessingException;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TeacherDaoImpl implements TeacherDao {

    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public TeacherDaoImpl() throws ClassNotFoundException {
    }

    @Override
    public Teacher save(Teacher teacher) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.SAVE_TEACHER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                teacher.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return teacher;
    }

    @Override
    public Optional<Teacher> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.FIND_TEACHER_BY_ID_WITH_STUDENTS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Teacher teacher = null;
            List<Student> students = new LinkedList<>();
            while (resultSet.next()) {
                if (teacher == null) {
                    teacher = createTeacher(id, resultSet);
                    teacher.setStudents(students);
                }
                int studentId = resultSet.getInt("student_id");
                if (studentId != 0) {
                    Student student = createStudent(studentId, resultSet);
                    students.add(student);
                }
            }
            return Optional.ofNullable(teacher);
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.FIND_ALL_TEACHER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = Teacher.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .build();
                teachers.add(teacher);
            }
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
        return teachers;
    }

    @Override
    public Teacher update(int id, Teacher teacher) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.UPDATE_TEACHER_BY_ID)) {
            findById(id);

            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
        return teacher;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.DELETE_TEACHER_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public void setStudentForTeacher(int teacherId, int studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.SET_STUDENT_FOR_TEACHER)) {
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQueries.DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    private Teacher createTeacher(int teacherId, ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName(resultSet.getString("first_name"));
        teacher.setLastName(resultSet.getString("last_name"));
        return teacher;
    }

    private Student createStudent(int studentId, ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName(resultSet.getString("student_first_name"));
        student.setLastName(resultSet.getString("student_last_name"));
        return student;
    }
}
