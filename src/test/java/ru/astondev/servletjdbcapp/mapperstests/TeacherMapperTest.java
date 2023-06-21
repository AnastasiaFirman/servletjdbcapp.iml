package ru.astondev.servletjdbcapp.mapperstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.ShortTeacherDto;
import ru.astondev.servletjdbcapp.dto.TeacherDto;
import ru.astondev.servletjdbcapp.mappers.TeacherMapper;
import ru.astondev.servletjdbcapp.mappers.impl.TeacherMapperImpl;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherMapperTest {
    private final TeacherMapper teacherMapper;

    public TeacherMapperTest() {
        teacherMapper = new TeacherMapperImpl();
    }

    @Test
    void toTeacherDtoTest() {
        Teacher teacher = new Teacher(1, "Почтальон", "Печкин");
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1, "Кот", "Матроскин");
        Student student2 = new Student(2, "Пес", "Шарик");
        students.add(student1);
        students.add(student2);
        teacher.setStudents(students);
        TeacherDto teacherDto = teacherMapper.toTeacherDto(teacher);
        Assertions.assertEquals(teacher.getId(), teacherDto.getId());
        Assertions.assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        Assertions.assertEquals(teacher.getLastName(), teacherDto.getLastName());

        List<ShortStudentDto> studentDtoList = teacherDto.getStudents();
        Assertions.assertNotNull(studentDtoList);
        Assertions.assertEquals(2, studentDtoList.size());

        ShortStudentDto studentDto1 = studentDtoList.get(0);
        Assertions.assertEquals(student1.getId(), studentDto1.getId());
        Assertions.assertEquals(student1.getFirstName(), studentDto1.getFirstName());
        Assertions.assertEquals(student1.getLastName(), studentDto1.getLastName());

        ShortStudentDto studentDto2 = studentDtoList.get(1);
        Assertions.assertEquals(student2.getId(), studentDto2.getId());
        Assertions.assertEquals(student2.getFirstName(), studentDto2.getFirstName());
        Assertions.assertEquals(student2.getLastName(), studentDto2.getLastName());
    }

    @Test
    void testToShortTeacherDto() {
        Teacher teacher = new Teacher(1, "Почтальон", "Печкин");
        ShortTeacherDto shortTeacherDto = teacherMapper.toShortTeacherDto(teacher);
        Assertions.assertEquals(teacher.getId(), shortTeacherDto.getId());
        Assertions.assertEquals(teacher.getFirstName(), shortTeacherDto.getFirstName());
        Assertions.assertEquals(teacher.getLastName(), shortTeacherDto.getLastName());
    }

    @Test
    void testToEntity() {
        ShortTeacherDto shortTeacherDto = new ShortTeacherDto(1, "Почтальон", "Печкин");
        Teacher teacher = teacherMapper.toEntity(shortTeacherDto);
        Assertions.assertEquals(shortTeacherDto.getId(), teacher.getId());
        Assertions.assertEquals(shortTeacherDto.getFirstName(), teacher.getFirstName());
        Assertions.assertEquals(shortTeacherDto.getLastName(), teacher.getLastName());
    }
}
