package ru.astondev.servletjdbcapp.mappers;

import org.mapstruct.Mapper;
import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.StudentDto;
import ru.astondev.servletjdbcapp.model.Student;

@Mapper
public interface StudentMapper {
    StudentDto toStudentDto(Student student);
    ShortStudentDto toShortStudentDto(Student student);
    Student toEntity(ShortStudentDto shortStudentDto);
}
