package ru.astondev.servletjdbcapp.mapperstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.astondev.servletjdbcapp.dto.BookDto;
import ru.astondev.servletjdbcapp.mappers.BookMapper;
import ru.astondev.servletjdbcapp.mappers.impl.BookMapperImpl;
import ru.astondev.servletjdbcapp.model.Book;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookMapperTest {
    private final BookMapper bookMapper;
     public BookMapperTest() {
         bookMapper = new BookMapperImpl();
     }

     @Test
     void toBookDtoTest() {
         Book book = new Book(1, "Граф Монте Кристо", "Александр Дюма");
         BookDto bookDto = bookMapper.toBookDto(book);
         Assertions.assertEquals(book.getId(), bookDto.getId());
         Assertions.assertEquals(book.getTitle(), bookDto.getTitle());
         Assertions.assertEquals(book.getAuthor(), bookDto.getAuthor());
     }

     @Test
     void toEntityTest() {
         BookDto bookDto = new BookDto(1, "Граф Монте Кристо", "Александр Дюма");
         Book book = bookMapper.toEntity(bookDto);
         Assertions.assertEquals(bookDto.getId(), book.getId());
         Assertions.assertEquals(bookDto.getTitle(), book.getTitle());
         Assertions.assertEquals(bookDto.getAuthor(), book.getAuthor());
     }
}
