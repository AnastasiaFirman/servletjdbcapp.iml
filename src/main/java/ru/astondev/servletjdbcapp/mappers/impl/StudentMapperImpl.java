package ru.astondev.servletjdbcapp.mappers.impl;

import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.StudentDto;
import ru.astondev.servletjdbcapp.mappers.StudentMapper;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDto toStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        List<Book> books = student.getBooks();
        if (books != null && !books.isEmpty()) {
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : books) {
                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());
                bookDto.setTitle(book.getTitle());
                bookDto.setAuthor(book.getAuthor());
                bookDtos.add(bookDto);
            }
            studentDto.setBooks(bookDtos);
        }
        return studentDto;
    }

    @Override
    public ShortStudentDto toShortStudentDto(Student student) {
        ShortStudentDto shortStudentDto = new ShortStudentDto();
        shortStudentDto.setId(student.getId());
        shortStudentDto.setFirstName(student.getFirstName());
        shortStudentDto.setLastName(student.getLastName());
        return shortStudentDto;
    }

    @Override
    public Student toEntity(ShortStudentDto shortStudentDto) {
        Student student = new Student();
        student.setId(shortStudentDto.getId());
        student.setFirstName(shortStudentDto.getFirstName());
        student.setLastName(shortStudentDto.getLastName());
        return student;
    }
}
