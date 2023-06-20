package ru.astondev.servletjdbcapp.mappers;

import org.mapstruct.Mapper;
import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.model.Book;

@Mapper
public interface BookMapper {
    BookDto toBookDto(Book book);
    Book toEntity(BookDto bookDto);
}
