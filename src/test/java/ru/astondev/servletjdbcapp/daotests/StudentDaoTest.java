package ru.astondev.servletjdbcapp.daotests;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dao.*;
import ru.astondev.servletjdbcapp.dao.impl.BookDaoImpl;
import ru.astondev.servletjdbcapp.dao.impl.StudentDaoImpl;
import ru.astondev.servletjdbcapp.dao.impl.TeacherDaoImpl;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDaoTest {
    private final StudentDao studentDao;
    private final BookDao bookDao;
    private final TeacherDao teacherDao;
    private final DataSource dataSource = DatasourceConnector.getDataSource();
    public StudentDaoTest() throws ClassNotFoundException {
        bookDao = new BookDaoImpl();
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
        bookDao.deleteAll();
    }

    @Test
    void saveTest() {
        Student student = new Student("Вася", "Пупкин");
        Student studentForSave = studentDao.save(student);
        Assertions.assertEquals(student, studentForSave);
    }

    @Test
    void findByIdTest() {
        Student student = new Student("Вася", "Пупкин");
        Student savedStudent = studentDao.save(student);
        Book book = new Book ("Граф Монте Кристо", "Александр Дюма");
        Book savedBook = bookDao.save(book);
        bookDao.setStudentForBook(savedStudent.getId(), savedBook.getId());
        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        savedStudent.setBooks(books);
        Student foundStudent = studentDao.findById(savedStudent.getId()).get();
        Assertions.assertEquals(savedStudent.getId(), foundStudent.getId());
        Assertions.assertEquals(savedStudent.getFirstName(), foundStudent.getFirstName());
        Assertions.assertEquals(savedStudent.getLastName(), foundStudent.getLastName());
        Assertions.assertEquals(1, books.size());
        Book foundBook = books.get(0);
        Assertions.assertEquals(savedBook, foundBook);
    }

    @Test
    void findAllTest() {
        studentDao.save(new Student("Пес", "Шарик"));
        studentDao.save(new Student("Кот", "Матроскин"));
        Assertions.assertEquals(2, studentDao.findAll().size());
    }

    @Test
    void updateTest() {
        Student student = new Student("Вася", "Пупкин");
        Student savedStudent = studentDao.save(student);
        Student expectedStudent = new Student(savedStudent.getId(), "Василий", "Пупкин");
        Student updatedStudent = studentDao.update(savedStudent.getId(), expectedStudent);
        Assertions.assertEquals(expectedStudent.getId(), updatedStudent.getId());
        Assertions.assertEquals(expectedStudent.getFirstName(), updatedStudent.getFirstName());
        Assertions.assertEquals(expectedStudent.getLastName(), updatedStudent.getLastName());
    }

    @Test
    void deleteByIdTest() {
        Student savedStudent = studentDao.save(new Student("Вася", "Пупкин"));
        studentDao.deleteById(savedStudent.getId());
        Assertions.assertEquals(0, studentDao.findAll().size());
    }

    @Test
    void untieStudentFromTeacher() {
        Student student = new Student("Кот", "Матроскин");
        Teacher teacher = new Teacher("Почтальон", "Печкин");
        Student savedStudent = studentDao.save(student);
        Teacher savedTeacher = teacherDao.save(teacher);
        List<Student> students = new ArrayList<>();
        students.add(savedStudent);
        savedTeacher.setStudents(students);
        teacherDao.save(savedTeacher);
        studentDao.untieStudentFromTeacher(savedStudent.getId());
        savedTeacher = teacherDao.findById(savedTeacher.getId()).get();
        Assertions.assertEquals(0, savedTeacher.getStudents().size());
    }
}
