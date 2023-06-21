package ru.astondev.servletjdbcapp.dbutils;

public class TeacherSqlQueries {
    public static final String SAVE_TEACHER =
            "insert into teacher (first_name, last_name) values(?,?);";
    public static final String FIND_TEACHER_BY_ID_WITH_STUDENTS = "select t.id teacher_id, t.first_name, t.last_name, s.id student_id, s.first_name as student_first_name, s.last_name as student_last_name " +
            "from teacher t left join student_teacher_binding stb on t.id = stb.teacher_id left join student s on stb.student_id = s.id " +
            "where t.id = ?;";
    public static final String FIND_ALL_TEACHER = "SELECT * FROM teacher;";
    public static final String UPDATE_TEACHER_BY_ID = "update teacher set first_name = ?, last_name = ? where id = ?;";
    public static final String DELETE_TEACHER_BY_ID = "DELETE FROM teacher where id = ?;";
    public static final String SET_STUDENT_FOR_TEACHER = "insert into student_teacher_binding (teacher_id, student_id) values (?, ?);";
    public static final String DELETE_ALL = "delete from teacher;";
    public static final String UNTIE_STUDENTS_FROM_TEACHER = "DELETE FROM student_teacher_binding WHERE teacher_id = ?";
}
