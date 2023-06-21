package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Student save(Student student);

    Optional<Student> findById(int id);

    List<Student> findAll();

    Student update(int id, Student student);

    void deleteById(int id) throws SQLException;

    void untieStudentFromTeacher(int studentId, int teacherId);

    void deleteAll();
}
