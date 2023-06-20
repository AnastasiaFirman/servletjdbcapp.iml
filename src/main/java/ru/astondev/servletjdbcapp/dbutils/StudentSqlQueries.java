package ru.astondev.servletjdbcapp.dbutils;

public final class StudentSqlQueries {
    public static final String SAVE_STUDENT = "insert into student (first_name, last_name) values(?,?);";
    public static final String FIND_ALL_STUDENTS = "SELECT * FROM student;";
    public static final String DELETE_STUDENT_BY_ID = "DELETE FROM student where id = ?;";
    public static final String FIND_STUDENT_BY_ID_WITH_BOOKS = "select s.id student_id, s.first_name, s.last_name, b.id book_id, b.title, b.author" +
            " from student s left join book b on s.id = b.student_id where s.id = ?;";
    public static final String UPDATE_STUDENT_BY_ID = "update student set first_name = ?, last_name = ? where id = ?;";
    public static final String SET_STUDENT_FOR_TEACHER_TO_NULL = "update student_teacher_binding set teacher_id = null, student_id = null where student_id = ?;";
    public static final String DELETE_ALL = "delete from student;";

    private StudentSqlQueries() {
    }
}
