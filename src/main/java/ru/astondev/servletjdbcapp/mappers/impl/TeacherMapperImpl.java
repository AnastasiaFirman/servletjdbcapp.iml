package ru.astondev.servletjdbcapp.mappers.impl;

import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.ShortTeacherDto;
import ru.astondev.servletjdbcapp.dto.TeacherDto;
import ru.astondev.servletjdbcapp.mappers.TeacherMapper;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;

import java.util.LinkedList;
import java.util.List;

public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public TeacherDto toTeacherDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        List<Student> students = teacher.getStudents();
        if (students != null && !students.isEmpty()) {
            List<ShortStudentDto> studentDtoList = new LinkedList<>();
            for (Student student : students) {
                ShortStudentDto shortStudentDto = new ShortStudentDto();
                shortStudentDto.setId(student.getId());
                shortStudentDto.setFirstName(student.getFirstName());
                shortStudentDto.setLastName(student.getLastName());
                studentDtoList.add(shortStudentDto);
            }
            teacherDto.setStudents(studentDtoList);
        }
        return teacherDto;
    }

    @Override
    public ShortTeacherDto toShortTeacherDto(Teacher teacher) {
        ShortTeacherDto shortTeacherDto = new ShortTeacherDto();
        shortTeacherDto.setId(teacher.getId());
        shortTeacherDto.setFirstName(teacher.getFirstName());
        shortTeacherDto.setLastName(teacher.getLastName());
        return shortTeacherDto;
    }

    @Override
    public Teacher toEntity(ShortTeacherDto shortTeacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(shortTeacherDto.getId());
        teacher.setFirstName(shortTeacherDto.getFirstName());
        teacher.setLastName(shortTeacherDto.getLastName());
        return teacher;
    }
}
