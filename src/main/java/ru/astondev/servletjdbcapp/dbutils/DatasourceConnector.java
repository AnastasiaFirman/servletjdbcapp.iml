package ru.astondev.servletjdbcapp.dbutils;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.astondev.servletjdbcapp.exception.SqlProcessingException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class DatasourceConnector {
    //TODO use singleton
    public static DataSource getDataSource() throws ClassNotFoundException {
        Properties properties = getProperties();
        Class.forName(properties.getProperty("driverClassName"));
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        if (Boolean.getBoolean(properties.getProperty("isTestConfig"))) {
            createTestTables(dataSource);
        }
        return dataSource;
    }
    //TODO + create table if not exists
    public static void createTestTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS book;");
            statement.execute("DROP TABLE IF EXISTS student_teacher_binding;");
            statement.execute("DROP TABLE IF EXISTS student;DROP TABLE IF EXISTS teacher;");
            statement.execute("");
            statement.execute("CREATE TABLE student (id INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(255), last_name VARCHAR(255))");
            statement.execute("CREATE TABLE book (id INT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(255), author VARCHAR(255), student_id INT)");
            statement.execute("CREATE TABLE teacher (id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255))");
            statement.execute("CREATE TABLE student_teacher_binding (student_id INT, teacher_id INT, PRIMARY KEY(student_id, teacher_id))");
        } catch (Exception e) {
            throw new SqlProcessingException(e);
        }
    }
    private static Properties getProperties() {
        try (InputStream fis = DatasourceConnector.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
