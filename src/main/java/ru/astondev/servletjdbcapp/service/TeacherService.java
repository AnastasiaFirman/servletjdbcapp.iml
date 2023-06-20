package ru.astondev.servletjdbcapp.service;

import ru.astondev.servletjdbcapp.model.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Teacher findById (int id);
    List<Teacher> findAll();
    Teacher update(int id, Teacher teacher);
    void deleteById(int id);
    void setStudentForTeacher(int teacherId, int studentId);
    void deleteAll();
}
