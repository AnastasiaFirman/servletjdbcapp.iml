package ru.astondev.servletjdbcapp.servicetests;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;
import ru.astondev.servletjdbcapp.service.StudentService;
import ru.astondev.servletjdbcapp.service.TeacherService;
import ru.astondev.servletjdbcapp.service.impl.StudentServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.TeacherServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherServiceTest {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final DataSource dataSource = DatasourceConnector.getDataSource();
    public TeacherServiceTest() throws ClassNotFoundException {
        studentService = new StudentServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @BeforeAll
    void createDb() {
        DatasourceConnector.createTestTables(dataSource);
    }

    @AfterEach
    void deleteAll() {
        studentService.deleteAll();
        teacherService.deleteAll();
    }

    @Test
    void saveTest() {
        Teacher teacher = new Teacher("Вася", "Пупкин");
        Teacher savedTeacher = teacherService.save(teacher);
        Assertions.assertEquals(teacher, savedTeacher);
    }

    @Test
    void findByIdTest() {
        Teacher teacher = new Teacher("Вася", "Пупкин");
        Teacher savedTeacher = teacherService.save(teacher);
        Student student = new Student("Кот", "Матроскин");
        Student savedStudent = studentService.save(student);
        teacherService.setStudentForTeacher(savedTeacher.getId(), savedStudent.getId());
        List<Student> students = new ArrayList<>();
        students.add(savedStudent);
        savedTeacher.setStudents(students);
        Teacher foundTeacher = teacherService.findById(savedTeacher.getId());
        Assertions.assertEquals(savedTeacher.getId(), foundTeacher.getId());
        Assertions.assertEquals(savedTeacher.getFirstName(), foundTeacher.getFirstName());
        Assertions.assertEquals(savedTeacher.getLastName(), foundTeacher.getLastName());
        Assertions.assertEquals(1, students.size());
        Student foundStudent = students.get(0);
        Assertions.assertEquals(savedStudent, foundStudent);
    }

    @Test
    void findAllTest() {
        teacherService.save(new Teacher("Пес", "Шарик"));
        teacherService.save(new Teacher("Кот", "Матроскин"));
        Assertions.assertEquals(2, teacherService.findAll().size());
    }

    @Test
    void updateTest() {
        Teacher savedTeacher = teacherService.save(new Teacher("Вася", "Пупкин"));
        Teacher expectedTeacher = new Teacher(savedTeacher.getId(), "Василий", "Пупкин");
        Teacher updatedTeacher = teacherService.update(savedTeacher.getId(), expectedTeacher);
        Assertions.assertEquals(expectedTeacher, updatedTeacher);
    }

    @Test
    void setStudentForTeacherTest() {
        Student student = studentService.save(new Student("Кот", "Матроскин"));
        Teacher teacher = teacherService.save(new Teacher("Почтальон", "Печкин"));
        teacherService.setStudentForTeacher(teacher.getId(), student.getId());
        List<Student> students = new ArrayList<>();
        students.add(student);
        teacher.setStudents(students);
        Assertions.assertEquals(teacher.getStudents().get(0), student);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Teacher teacher = teacherService.save(new Teacher("Почтальон", "Печкин"));
        teacherService.deleteById(teacher.getId());
        Assertions.assertEquals(0, teacherService.findAll().size());
    }




}
