package ru.astondev.servletjdbcapp.dao;

import ru.astondev.servletjdbcapp.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {
    Teacher save(Teacher teacher);

    Optional<Teacher> findById(int id);

    List<Teacher> findAll();

    Teacher update(int id, Teacher teacher);

    void deleteById(int id);

    void setStudentForTeacher(int teacherId, int studentId);

    void deleteAll();
}
