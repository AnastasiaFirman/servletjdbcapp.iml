package ru.astondev.servletjdbcapp.web;

import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.mappers.BookMapper;
import ru.astondev.servletjdbcapp.model.Book;
import ru.astondev.servletjdbcapp.service.BookService;
import ru.astondev.servletjdbcapp.service.impl.BookServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Path("/book")
public class BookResource {
    private final BookService bookService = new BookServiceImpl();
    @Inject
    private BookMapper bookMapper;

    public BookResource() throws ClassNotFoundException {
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Book findById(@PathParam("id") int id) {
        return bookService.findById(id);
    }

    @GET
    @Produces("application/json")
    public List<BookDto> findAll() {
        List<Book> books = bookService.findAll();
        return books.stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    @PUT
    @Path("/{studentId}/{bookId}")
    @Consumes("application/json")
    public void setStudentForBook(@PathParam("studentId") int studentId, @PathParam("bookId") int bookId) {
        bookService.setStudentForBook(studentId, bookId);
    }

    @PUT
    @Path("/untie/{id}")
    @Consumes("application/json")
    public void untieBookFromStudent(@PathParam("id") int id) {
        bookService.untieBookFromStudent(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public BookDto update(@PathParam("id") int id, BookDto bookDto) {
        return bookMapper.toBookDto(bookService.update(id, bookMapper.toEntity(bookDto)));
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public BookDto save(BookDto bookDto) {
        return bookMapper.toBookDto(bookService.save(bookMapper.toEntity(bookDto)));
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void deleteById(@PathParam("id") int id) {
        bookService.deleteById(id);
    }
}
