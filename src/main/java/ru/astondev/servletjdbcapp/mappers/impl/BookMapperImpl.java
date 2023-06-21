package ru.astondev.servletjdbcapp.mappers.impl;

import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.mappers.BookMapper;
import ru.astondev.servletjdbcapp.model.Book;

public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        return book;
    }
}
