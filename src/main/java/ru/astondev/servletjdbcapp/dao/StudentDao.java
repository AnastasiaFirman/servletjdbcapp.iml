package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Student save(Student student);

    Optional<Student> findById(int id);

    List<Student> findAll();

    Student update(int id, Student student);

    void deleteById(int id);

    void untieStudentFromTeacher(int studentId);

    void deleteAll();
}
