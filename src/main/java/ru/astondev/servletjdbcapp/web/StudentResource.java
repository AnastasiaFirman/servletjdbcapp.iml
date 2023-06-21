package ru.astondev.servletjdbcapp.web;

import ru.astondev.servletjdbcapp.dto.ShortStudentDto;
import ru.astondev.servletjdbcapp.dto.StudentDto;
import ru.astondev.servletjdbcapp.mappers.StudentMapper;
import ru.astondev.servletjdbcapp.model.Student;
import ru.astondev.servletjdbcapp.service.StudentService;
import ru.astondev.servletjdbcapp.service.impl.StudentServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Path("/student")
public class StudentResource {
    private final StudentService studentService = new StudentServiceImpl();
    @Inject
    private StudentMapper studentMapper;

    public StudentResource() throws ClassNotFoundException {

    }

    @GET
    @Produces("application/json")
    public List<ShortStudentDto> findAll() {
        List<Student> students = studentService.findAll();
        return students.stream().map(studentMapper::toShortStudentDto).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public StudentDto findById(@PathParam("id") int id) {
        return studentMapper.toStudentDto(studentService.findById(id));
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ShortStudentDto save(ShortStudentDto student) {
        return studentMapper.toShortStudentDto(studentService.save(studentMapper.toEntity(student)));
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public ShortStudentDto update(@PathParam("id") int id, ShortStudentDto student) {
        return studentMapper.toShortStudentDto(studentService.update(id, studentMapper.toEntity(student)));
    }


    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") int id) throws SQLException {
        studentService.deleteById(id);
    }

    @PUT
    @Path("/untie/{studentId}/{teacherId}")
    public void untieStudentFromTeacher(@PathParam("studentId") int studentId, @PathParam("teacherId") int teacherId) {
        studentService.untieStudentFromTeacher(studentId, teacherId);
    }
}
