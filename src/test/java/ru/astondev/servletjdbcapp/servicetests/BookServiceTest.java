package ru.astondev.servletjdbcapp.servicetests;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.service.BookService;
import ru.astondev.servletjdbcapp.service.StudentService;
import ru.astondev.servletjdbcapp.service.impl.BookServiceImpl;
import ru.astondev.servletjdbcapp.service.impl.StudentServiceImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {
    private final StudentService studentService;
    private final BookService bookService;
    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public BookServiceTest() throws ClassNotFoundException {
        bookService = new BookServiceImpl();
        studentService = new StudentServiceImpl();
    }

    @BeforeAll
    void createDb() {
        DatasourceConnector.createTestTables(dataSource);
    }

    @AfterEach
    void deleteAll() {
        studentService.deleteAll();
        bookService.deleteAll();
    }

    @Test
    void saveTest() {
        Book book = new Book("Каникулы в Простоквашино", "Эдуард Успенский");
        Book savedBook = bookService.save(book);
        Assertions.assertEquals(book, savedBook);
    }

    @Test
    void findByIdTest() {
        Book savedBook = bookService.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        Book foundBook = bookService.findById(savedBook.getId());
        Assertions.assertEquals(savedBook.getTitle(), foundBook.getTitle());
        Assertions.assertEquals(savedBook.getAuthor(), foundBook.getAuthor());
    }

    @Test
    void findAllTest() {
        bookService.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookService.save(new Book("Граф Монте Кристо", "Александр Дюма"));
        Assertions.assertEquals(2, bookService.findAll().size());
    }

    @Test
    void deleteByIdTest() {
        Book book = bookService.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookService.deleteById(book.getId());
        Assertions.assertEquals(0, bookService.findAll().size());
    }

    @Test
    void updateTest() {
        Book book = bookService.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        Book expectedBook = new Book(book.getId(), "Зима в Простоквашино", "Эдуард Успенский");
        Book updatedBook = bookService.update(book.getId(), expectedBook);
        Assertions.assertEquals(expectedBook, updatedBook);
    }

    @Test
    void setStudentForBookTest() {
        Student student = studentService.save(new Student("Кот", "Матроскин"));
        Book book = bookService.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookService.setStudentForBook(book.getId(), student.getId());
        List<Book> books = new ArrayList<>();
        books.add(book);
        student.setBooks(books);
        Assertions.assertEquals(student.getBooks().get(0), book);

    }

    @Test
    void untieBookFromStudent() {
        Book book = new Book("Каникулы в Простоквашино", "Эдуард Успенский");
        Student student = new Student("Кот", "Матроскин");
        Book savedBook = bookService.save(book);
        Student savedStudent = studentService.save(student);
        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        savedStudent.setBooks(books);
        studentService.save(savedStudent);
        bookService.untieBookFromStudent(savedBook.getId());
        savedStudent = studentService.findById(savedStudent.getId());
        Assertions.assertEquals(0, savedStudent.getBooks().size());
    }


}
