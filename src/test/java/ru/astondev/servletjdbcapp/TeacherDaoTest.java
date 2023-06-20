package ru.astondev.servletjdbcapp;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dao.StudentDao;
import ru.astondev.servletjdbcapp.dao.StudentDaoImpl;
import ru.astondev.servletjdbcapp.dao.TeacherDao;
import ru.astondev.servletjdbcapp.dao.TeacherDaoImpl;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherDaoTest {
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;

    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public TeacherDaoTest() throws ClassNotFoundException {
        studentDao = new StudentDaoImpl();
        teacherDao = new TeacherDaoImpl();
    }

    @BeforeAll
    void createDb() {
        DatasourceConnector.createTestTables(dataSource);
    }

    @AfterEach
    void deleteAll() {
        studentDao.deleteAll();
        teacherDao.deleteAll();
    }

    @Test
    void saveTest() {
        Teacher teacher = new Teacher("Вася", "Пупкин");
        Teacher savedTeacher = teacherDao.save(teacher);
        Assertions.assertEquals(teacher.getFirstName(), savedTeacher.getFirstName());
        Assertions.assertEquals(teacher.getLastName(), savedTeacher.getLastName());
    }

    @Test
    void findByIdTest() {
        Teacher teacher = new Teacher("Вася", "Пупкин");
        Teacher savedTeacher = teacherDao.save(teacher);
        Student student = new Student("Кот", "Матроскин");
        Student savedStudent = studentDao.save(student);
        teacherDao.setStudentForTeacher(savedTeacher.getId(), savedStudent.getId());
        List<Student> students = new ArrayList<>();
        students.add(savedStudent);
        savedTeacher.setStudents(students);
        Teacher foundTeacher = teacherDao.findById(savedTeacher.getId()).get();
        Assertions.assertEquals(savedTeacher.getId(), foundTeacher.getId());
        Assertions.assertEquals(savedTeacher.getFirstName(), foundTeacher.getFirstName());
        Assertions.assertEquals(savedTeacher.getLastName(), foundTeacher.getLastName());
        Assertions.assertEquals(1, students.size());
        Student foundStudent = students.get(0);
        Assertions.assertEquals(savedStudent.getId(), foundStudent.getId());
        Assertions.assertEquals(savedStudent.getFirstName(), foundStudent.getFirstName());
        Assertions.assertEquals(savedStudent.getLastName(), foundStudent.getLastName());
    }

    @Test
    void findAllTest() {
        teacherDao.save(new Teacher("Пес", "Шарик"));
        teacherDao.save(new Teacher("Кот", "Матроскин"));
        Assertions.assertEquals(2, teacherDao.findAll().size());
    }

    @Test
    void updateTest() {
        Teacher savedTeacher = teacherDao.save(new Teacher("Вася", "Пупкин"));
        Teacher expectedTeacher = new Teacher(savedTeacher.getId(), "Василий", "Пупкин");
        Teacher updatedTeacher = teacherDao.update(savedTeacher.getId(), expectedTeacher);
        Assertions.assertEquals(expectedTeacher.getId(), updatedTeacher.getId());
        Assertions.assertEquals(expectedTeacher.getFirstName(), updatedTeacher.getFirstName());
        Assertions.assertEquals(expectedTeacher.getLastName(), updatedTeacher.getLastName());
    }

    @Test
    void setStudentForTeacherTest() {
        Student student = studentDao.save(new Student("Кот", "Матроскин"));
        Teacher teacher = teacherDao.save(new Teacher("Почтальон", "Печкин"));
        teacherDao.setStudentForTeacher(teacher.getId(), student.getId());
        List<Student> students = new ArrayList<>();
        students.add(student);
        teacher.setStudents(students);
        Assertions.assertEquals(teacher.getStudents().get(0), student);
    }

    @Test
    void deleteByIdTest() {
        Teacher teacher = teacherDao.save(new Teacher("Почтальон", "Печкин"));
        teacherDao.deleteById(teacher.getId());
        Assertions.assertEquals(0, teacherDao.findAll().size());
    }
}
