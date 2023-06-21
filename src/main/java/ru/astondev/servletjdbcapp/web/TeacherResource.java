package ru.astondev.servletjdbcapp.web;

import ru.astondev.servletjdbcapp.dto.ShortTeacherDto;
import ru.astondev.servletjdbcapp.dto.TeacherDto;
import ru.astondev.servletjdbcapp.mappers.TeacherMapper;
import ru.astondev.servletjdbcapp.model.Teacher;
import ru.astondev.servletjdbcapp.service.TeacherService;
import ru.astondev.servletjdbcapp.service.impl.TeacherServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Path("/teacher")
public class TeacherResource {
    private final TeacherService teacherService = new TeacherServiceImpl();
    @Inject
    private TeacherMapper teacherMapper;

    public TeacherResource() throws ClassNotFoundException {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ShortTeacherDto save (ShortTeacherDto shortTeacherDto) {
        return teacherMapper.toShortTeacherDto(teacherService.save(teacherMapper.toEntity(shortTeacherDto)));
    }
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public TeacherDto findById (@PathParam("id") int id) {
        return teacherMapper.toTeacherDto(teacherService.findById(id));
    }

    @PUT
    @Path("/{teacherId}/{studentId}")
    @Consumes("application/json")
    public void setStudentForTeacher (@PathParam("teacherId")int teacherId, @PathParam("studentId") int studentId) {
        teacherService.setStudentForTeacher(teacherId, studentId);
    }

    @GET
    @Produces("application/json")
    public List<ShortTeacherDto> findAll() {
        List<Teacher> teachers = teacherService.findAll();
        return teachers.stream().map(teacherMapper::toShortTeacherDto).collect(Collectors.toList());
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public ShortTeacherDto update(@PathParam("id") int id, ShortTeacherDto shortTeacherDto) {
        return teacherMapper.toShortTeacherDto(teacherService.update(id, teacherMapper.toEntity(shortTeacherDto)));
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") int id) throws SQLException {
        teacherService.deleteById(id);
    }

}
