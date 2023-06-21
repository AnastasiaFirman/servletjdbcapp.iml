package ru.astondev.servletjdbcapp.service.impl;

import ru.astondev.servletjdbcapp.dao.TeacherDao;
import ru.astondev.servletjdbcapp.dao.impl.TeacherDaoImpl;
import ru.astondev.servletjdbcapp.exception.TeacherNotFoundException;
import ru.astondev.servletjdbcapp.model.Teacher;
import ru.astondev.servletjdbcapp.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherDao teacherDao = new TeacherDaoImpl();

    public TeacherServiceImpl() throws ClassNotFoundException {
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherDao.save(teacher);
    }

    @Override
    public Teacher findById(int id) {
        return teacherDao.findById(id).orElseThrow(TeacherNotFoundException::new);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public Teacher update(int id, Teacher teacher) {
        return teacherDao.update(id, teacher);
    }

    @Override
    public void deleteById(int id) {
        teacherDao.deleteById(id);
    }

    @Override
    public void setStudentForTeacher(int teacherId, int studentId) {
       teacherDao.setStudentForTeacher(teacherId, studentId);
    }

    @Override
    public void deleteAll() {
        teacherDao.deleteAll();
    }
}
