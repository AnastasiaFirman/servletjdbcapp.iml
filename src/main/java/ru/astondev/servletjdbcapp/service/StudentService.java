package ru.astondev.servletjdbcapp.service;

import ru.astondev.servletjdbcapp.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    Student save(Student student);
    Student findById (int id);
    List<Student> findAll();
    Student update(int id, Student student);
    void deleteById(int id) throws SQLException;
    void untieStudentFromTeacher(int studentId, int teacherId);
    void deleteAll();
}
