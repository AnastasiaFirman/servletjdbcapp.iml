package ru.astondev.servletjdbcapp.service.impl;

import ru.astondev.servletjdbcapp.dao.StudentDao;
import ru.astondev.servletjdbcapp.dao.TeacherDao;
import ru.astondev.servletjdbcapp.dao.impl.StudentDaoImpl;
import ru.astondev.servletjdbcapp.dao.impl.TeacherDaoImpl;
import ru.astondev.servletjdbcapp.exception.StudentNotFoundException;
import ru.astondev.servletjdbcapp.exception.TeacherNotFoundException;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.service.StudentService;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao = new StudentDaoImpl();
    private final TeacherDao teacherDao = new TeacherDaoImpl();

    public StudentServiceImpl() throws ClassNotFoundException {
    }

    @Override
    public Student save(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Student findById(int id) {
        return studentDao.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student update(int id, Student student) {
        return studentDao.update(id, student);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        studentDao.deleteById(id);
    }

    @Override
    public void untieStudentFromTeacher(int studentId, int teacherId) {
        studentDao.findById(studentId).orElseThrow(StudentNotFoundException::new);
        teacherDao.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
        studentDao.untieStudentFromTeacher(studentId, teacherId);
    }

    @Override
    public void deleteAll() {
        studentDao.deleteAll();
    }
}
