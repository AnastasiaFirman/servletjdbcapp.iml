package ru.astondev.servletjdbcapp.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookDto {
    private int id;
    private String title;
    private String author;
}
