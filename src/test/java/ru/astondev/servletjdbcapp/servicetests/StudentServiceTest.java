package ru.astondev.servletjdbcapp.servicetests;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;
import ru.astondev.servletjdbcapp.service.BookService;
import ru.astondev.servletjdbcapp.service.StudentService;
import ru.astondev.servletjdbcapp.service.TeacherService;
import ru.astondev.servletjdbcapp.service.impl.BookServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.StudentServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.TeacherServiceImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {
    private final StudentService studentService;
    private final BookService bookService;
    private final TeacherService teacherService;
    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public StudentServiceTest() throws ClassNotFoundException {
        studentService = new StudentServiceImpl();
        bookService = new BookServiceImpl();
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
        bookService.deleteAll();
    }

    @Test
    void saveTest() {
        Student student = new Student("Вася", "Пупкин");
        Student studentForSave = studentService.save(student);
        Assertions.assertEquals(student, studentForSave);
    }

    @Test
    void findByIdTest() {
        Student student = new Student("Вася", "Пупкин");
        Student savedStudent = studentService.save(student);
        Book book = new Book ("Граф Монте Кристо", "Александр Дюма");
        Book savedBook = bookService.save(book);
        bookService.setStudentForBook(savedStudent.getId(), savedBook.getId());
        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        savedStudent.setBooks(books);
        Student foundStudent = studentService.findById(savedStudent.getId());
        Assertions.assertEquals(savedStudent.getId(), foundStudent.getId());
        Assertions.assertEquals(savedStudent.getFirstName(), foundStudent.getFirstName());
        Assertions.assertEquals(savedStudent.getLastName(), foundStudent.getLastName());
        Assertions.assertEquals(1, books.size());
        Book foundBook = books.get(0);
        Assertions.assertEquals(savedBook, foundBook);
    }

    @Test
    void findAllTest() {
        studentService.save(new Student("Пес", "Шарик"));
        studentService.save(new Student("Кот", "Матроскин"));
        Assertions.assertEquals(2, studentService.findAll().size());
    }

    @Test
    void updateTest() {
        Student student = new Student("Вася", "Пупкин");
        Student savedStudent = studentService.save(student);
        Student expectedStudent = new Student(savedStudent.getId(), "Василий", "Пупкин");
        Student updatedStudent = studentService.update(savedStudent.getId(), expectedStudent);
        Assertions.assertEquals(expectedStudent, updatedStudent);
    }

    @Test
    void deleteByIdTest() {
        Student savedStudent = studentService.save(new Student("Вася", "Пупкин"));
        studentService.deleteById(savedStudent.getId());
        Assertions.assertEquals(0, studentService.findAll().size());
    }

    @Test
    void untieStudentFromTeacher() {
        Student student = new Student("Кот", "Матроскин");
        Teacher teacher = new Teacher("Почтальон", "Печкин");
        Student savedStudent = studentService.save(student);
        Teacher savedTeacher = teacherService.save(teacher);
        List<Student> students = new ArrayList<>();
        students.add(savedStudent);
        savedTeacher.setStudents(students);
        teacherService.save(savedTeacher);
        studentService.untieStudentFromTeacher(savedStudent.getId());
        savedTeacher = teacherService.findById(savedTeacher.getId());
        Assertions.assertEquals(0, savedTeacher.getStudents().size());
    }

}
