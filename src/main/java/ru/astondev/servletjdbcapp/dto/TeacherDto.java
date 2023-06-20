package ru.astondev.servletjdbcapp.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TeacherDto {
    private int id;
    private String firstName;
    private String lastName;
    private List<ShortStudentDto> students;
}
