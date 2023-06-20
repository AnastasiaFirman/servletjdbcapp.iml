package ru.astondev.servletjdbcapp.mappers;

import org.mapstruct.Mapper;
import ru.astondev.servletjdbcapp.dto.ShortTeacherDto;
import ru.astondev.servletjdbcapp.dto.TeacherDto;
import ru.astondev.servletjdbcapp.model.Teacher;
@Mapper
public interface TeacherMapper {
    TeacherDto toTeacherDto (Teacher teacher);
    ShortTeacherDto toShortTeacherDto (Teacher teacher);
    Teacher toEntity (ShortTeacherDto shortTeacherDto);
}
