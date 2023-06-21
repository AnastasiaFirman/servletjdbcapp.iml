package ru.astondev.servletjdbcapp.daotests;

import org.junit.jupiter.api.*;
import ru.astondev.servletjdbcapp.dao.BookDao;
import ru.astondev.servletjdbcapp.dao.impl.BookDaoImpl;
import ru.astondev.servletjdbcapp.dao.StudentDao;
import ru.astondev.servletjdbcapp.dao.impl.StudentDaoImpl;
import ru.astondev.servletjdbcapp.dbutils.DatasourceConnector;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookDaoTest {
    private final StudentDao studentDao;
    private final BookDao bookDao;
    private final DataSource dataSource = DatasourceConnector.getDataSource();

    public BookDaoTest() throws ClassNotFoundException {
        bookDao = new BookDaoImpl();
        studentDao = new StudentDaoImpl();
    }

    @BeforeAll
    void createDb() {
        DatasourceConnector.createTestTables(dataSource);
    }

    @AfterEach
    void deleteAll() {
        studentDao.deleteAll();
        bookDao.deleteAll();
    }

    @Test
    void saveTest() {
        Book book = new Book("Каникулы в Простоквашино", "Эдуард Успенский");
        Book savedBook = bookDao.save(book);
        Assertions.assertEquals(book, savedBook);
    }

    @Test
    void findByIdTest() {
        Book savedBook = bookDao.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        Book foundBook = bookDao.findBookById(savedBook.getId()).get();
        Assertions.assertEquals(savedBook.getTitle(), foundBook.getTitle());
        Assertions.assertEquals(savedBook.getAuthor(), foundBook.getAuthor());
    }

    @Test
    void findAllTest() {
        bookDao.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookDao.save(new Book("Граф Монте Кристо", "Александр Дюма"));
        Assertions.assertEquals(2, bookDao.findAll().size());
    }

    @Test
    void deleteByIdTest() {
        Book book = bookDao.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookDao.deleteById(book.getId());
        Assertions.assertEquals(0, bookDao.findAll().size());
    }

    @Test
    void updateTest() {
        Book book = bookDao.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        Book expectedBook = new Book(book.getId(), "Зима в Простоквашино", "Эдуард Успенский");
        Book updatedBook = bookDao.update(book.getId(), expectedBook);
        Assertions.assertEquals(expectedBook, updatedBook);
    }

    @Test
    void setStudentForBookTest() {
        Student student = studentDao.save(new Student("Кот", "Матроскин"));
        Book book = bookDao.save(new Book("Каникулы в Простоквашино", "Эдуард Успенский"));
        bookDao.setStudentForBook(book.getId(), student.getId());
        List<Book> books = new ArrayList<>();
        books.add(book);
        student.setBooks(books);
        Assertions.assertEquals(student.getBooks().get(0), book);

    }

    @Test
    void untieBookFromStudent() {
        Book book = new Book("Каникулы в Простоквашино", "Эдуард Успенский");
        Student student = new Student("Кот", "Матроскин");
        Book savedBook = bookDao.save(book);
        Student savedStudent = studentDao.save(student);
        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        savedStudent.setBooks(books);
        studentDao.save(savedStudent);
        bookDao.untieBookFromStudent(savedBook.getId());
        savedStudent = studentDao.findById(savedStudent.getId()).get();
        Assertions.assertEquals(0, savedStudent.getBooks().size());
    }
}
