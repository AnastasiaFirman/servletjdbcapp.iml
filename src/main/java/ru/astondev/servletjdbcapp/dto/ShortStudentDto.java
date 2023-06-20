package ru.astondev.servletjdbcapp.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShortStudentDto {
    private int id;
    private String firstName;
    private String lastName;
}
