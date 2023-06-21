package ru.astondev.servletjdbcapp.dbutils;

public final class StudentSqlQueries {
    public static final String SAVE_STUDENT = "insert into student (first_name, last_name) values(?,?);";
    public static final String FIND_ALL_STUDENTS = "SELECT * FROM student;";
    public static final String DELETE_STUDENT_BY_ID = "DELETE FROM student where id = ?;";
    public static final String FIND_STUDENT_BY_ID_WITH_BOOKS = "select s.id student_id, s.first_name, s.last_name, b.id book_id, b.title, b.author" +
            " from student s left join book b on s.id = b.student_id where s.id = ?;";
    public static final String UPDATE_STUDENT_BY_ID = "update student set first_name = ?, last_name = ? where id = ?;";
    public static final String UNTIE_STUDENT_FROM_TEACHER = "delete from student_teacher_binding where student_id = ? and teacher_id =?;";
    public static final String DELETE_ALL = "delete from student;";
    public static final String UNTIE_TEACHERS_FROM_STUDENT = "DELETE FROM student_teacher_binding WHERE student_id = ?";

    private StudentSqlQueries() {
    }
}
