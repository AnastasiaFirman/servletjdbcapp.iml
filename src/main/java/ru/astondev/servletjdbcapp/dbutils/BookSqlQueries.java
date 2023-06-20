package ru.astondev.servletjdbcapp.dbutils;

public class BookSqlQueries {
    public static final String FIND_BOOK_BY_ID = "select * from book where id = ?;";
    public static final String SET_BOOK_FOR_STUDENT_TO_NULL = "update book set student_id = null where id = ?;";
    public static final String SET_STUDENT_FOR_BOOK = "update book set student_id = ? where id = ?;";
    public static final String SAVE_BOOK = "insert into book (title, author) values(?,?);";
    public static final String FIND_ALL_BOOKS = "SELECT * FROM book;";
    public static final String DELETE_BOOK_BY_ID = "DELETE FROM book where id = ?;";
    public static final String UPDATE_BOOK_BY_ID = "update book set title = ?, author = ? where id = ?;";
    public static final String DELETE_ALL = "delete from book;";
}
