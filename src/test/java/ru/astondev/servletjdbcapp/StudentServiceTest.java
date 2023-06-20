package ru.astondev.servletjdbcapp;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.service.BookService;
import ru.astondev.servletjdbcapp.service.StudentService;
import ru.astondev.servletjdbcapp.service.TeacherService;
import ru.astondev.servletjdbcapp.service.impl.BookServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.StudentServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.TeacherServiceImpl;

import javax.sql.DataSource;
//@NoArgsConstructor
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
        Assertions.assertEquals(student.getFirstName(), studentForSave.getFirstName());
        Assertions.assertEquals(student.getLastName(), studentForSave.getLastName());
    }



}
