package ru.astondev.servletjdbcapp.mapperstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.StudentDto;
import ru.astondev.servletjdbcapp.mappers.StudentMapper;
import ru.astondev.servletjdbcapp.mappers.impl.StudentMapperImpl;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentMapperTest {
    private final StudentMapper studentMapper;

    public StudentMapperTest() {
        studentMapper = new StudentMapperImpl();
    }

    @Test
    void toStudentDtoTest() {
        Student student = new Student(1, "Вася", "Пупкин");
        Book book1 = new Book(1, "Приключения Шерлока Холмса", "Артур Конан Дойл");
        Book book2 = new Book(2, "Унесенные ветром", "Маргарет Митчелл");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        student.setBooks(books);

        StudentDto studentDto = studentMapper.toStudentDto(student);
        Assertions.assertEquals(student.getId(), studentDto.getId());
        Assertions.assertEquals(student.getFirstName(), studentDto.getFirstName());
        Assertions.assertEquals(student.getLastName(), studentDto.getLastName());

        List<BookDto> bookDtos = studentDto.getBooks();
        Assertions.assertEquals(2, bookDtos.size());

        BookDto bookDto1 = bookDtos.get(0);
        Assertions.assertEquals(book1.getId(), bookDto1.getId());
        Assertions.assertEquals(book1.getTitle(), bookDto1.getTitle());
        Assertions.assertEquals(book1.getAuthor(), bookDto1.getAuthor());

        BookDto bookDto2 = bookDtos.get(1);
        Assertions.assertEquals(book2.getId(), bookDto2.getId());
        Assertions.assertEquals(book2.getTitle(), bookDto2.getTitle());
        Assertions.assertEquals(book2.getAuthor(), bookDto2.getAuthor());
    }

    @Test
    void toShortStudentDtoTest() {
        Student student = new Student(1, "Вася", "Пупкин");
        ShortStudentDto shortStudentDto = studentMapper.toShortStudentDto(student);

        Assertions.assertEquals(student.getId(), shortStudentDto.getId());
        Assertions.assertEquals(student.getFirstName(), shortStudentDto.getFirstName());
        Assertions.assertEquals(student.getLastName(), shortStudentDto.getLastName());
    }

    @Test
    void toEntityTest() {
        ShortStudentDto shortStudentDto = new ShortStudentDto(1, "Вася", "Пупкин");
        Student student = studentMapper.toEntity(shortStudentDto);

        Assertions.assertEquals(student.getId(), shortStudentDto.getId());
        Assertions.assertEquals(student.getFirstName(), shortStudentDto.getFirstName());
        Assertions.assertEquals(student.getLastName(), shortStudentDto.getLastName());
    }
}


